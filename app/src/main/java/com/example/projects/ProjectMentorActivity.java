package com.example.projects;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.projects.R;
import com.example.projects.server.DeleteProject;
import com.example.projects.server.GetProject;
import com.example.projects.server.SetCompleteProject;

public class ProjectMentorActivity extends AppCompatActivity implements OnProjectListener, OnConfirmListener {
    TextView name;
    TextView description;
    FloatingActionButton complete;
    public int USER_ID;
    Project project;
    TabLayout tabLayout;
    ViewPager viewPager;
    PageMentorAdapter pageAdapter;
    DialogFragment dialogFragment;
    OnObjectiveListener ObjectiveListener;

    final String TAG = "dig1";

    public int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getExtras().getInt("id");
        setContentView(R.layout.activity_project_mentor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_project);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tabLayout = (TabLayout)findViewById(R.id.tabs);
        TabItem tabComponents = (TabItem)findViewById(R.id.tabComponents);
        TabItem tabObjectives = (TabItem)findViewById(R.id.tabObjectives);
        TabItem tabTeams = (TabItem)findViewById(R.id.tabTeams);
        viewPager = (ViewPager)findViewById(R.id.viewPager);

        pageAdapter = new PageMentorAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), this);
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        name = (TextView) findViewById(R.id.title_nameProject);
        description = (TextView) findViewById(R.id.title_descriptionProject);
        complete = (FloatingActionButton) findViewById(R.id.complete_project);
        USER_ID = getIntent().getExtras().getInt("idUser");

        GetProject task = new GetProject(this,this, id);
        task.execute();
    }

    public void setProject() {
        name.setText(project.getName());
        description.setText(project.getDescription());
        if (project.isCompleted()){
            int drawable = getResources().getIdentifier("ic_baseline_check_24", "drawable", getPackageName());
            complete.setImageResource(drawable);
            complete.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.table_1_item_characteristics)));
        }
    }

    public void ButtonAdd(View view) {
        System.out.println("page: " + tabLayout.getSelectedTabPosition());
        dialogFragment = new ConfirmFragment(this, project.isCompleted() == false ? 0 : 1);
        dialogFragment.show(getSupportFragmentManager(), TAG);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.project_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.action_edit:

                Intent intent = new Intent(this, EditProject.class);
                intent.putExtra("id", id);
                startActivity(intent);
                return true;
            case R.id.action_delete_project:
                dialogFragment = new ConfirmFragment(this, 2);
                dialogFragment.show(getSupportFragmentManager(), TAG);
                return true;
            case android.R.id.home:
                Intent intent2 = new Intent();
                intent2.putExtra("name", project.getName());
                intent2.putExtra("description", project.getDescription());
                intent2.putExtra("completed", project.isCompleted());
                setResult(RESULT_OK, intent2);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent2 = new Intent();
        intent2.putExtra("name", project.getName());
        intent2.putExtra("description", project.getDescription());
        intent2.putExtra("completed", project.isCompleted());
        setResult(RESULT_OK, intent2);
        this.finish();
    }

    @Override
    public void onProjectCompleted(Project proj) {
        project = proj;
        setProject();
    }


    @Override
    public void onProjectError(String error) {

    }

    @Override
    public void OnConfirmPositive() {
        boolean compl = project.isCompleted();
        if (!project.isCompleted()) {
            int drawable = getResources().getIdentifier("ic_baseline_check_24", "drawable", getPackageName());
            complete.setImageResource(drawable);
            complete.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.table_1_item_characteristics)));
            SetCompleteProject setCompleteProject = new SetCompleteProject(this, id, 1);
            setCompleteProject.execute();
        }
        else {
            int drawable = getResources().getIdentifier("ic_baseline_check_24", "drawable", getPackageName());
            complete.setImageResource(drawable);
            complete.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.floatingButton)));
            SetCompleteProject setCompleteProject = new SetCompleteProject(this, id, 0);
            setCompleteProject.execute();
        }
        project.setCompleted(!compl);
    }

    @Override
    public void OnConfirmNegative() {

    }

    @Override
    public void OnConfirmDelete() {
        DeleteProject deleteProject = new DeleteProject(this, id);
        deleteProject.execute();
        //
        Intent intent1 = new Intent();
        intent1.putExtra("delete", true);
        setResult(RESULT_OK, intent1);
        this.finish();
    }
}