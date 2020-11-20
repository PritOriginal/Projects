package com.example.kvantorium;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.kvantorium.R;
import com.example.kvantorium.server.DeleteProject;
import com.example.kvantorium.server.GetProject;
import com.example.kvantorium.server.SetCompleteProject;

import static android.support.constraint.Constraints.TAG;

public class ProjectMentorActivity extends AppCompatActivity implements OnProjectListener, OnConfirmListener {
    TextView name;
    TextView description;
    FloatingActionButton complete;
    public int USER_ID;
    DBHelper dbHelper;
    SQLiteDatabase database;
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
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_project);
//        setSupportActionBar(toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

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
        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();
        USER_ID = getIntent().getExtras().getInt("idUser");

        GetProject task = new GetProject(this, id);
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

    public void AddComponents() {
        Intent intent2 = new Intent(this, AddComponent.class);
        intent2.putExtra("id", id);
        startActivity(intent2);
    }
    public void AddObjective() {
        dialogFragment = new AddObjectiveFragment(ObjectiveListener);
        dialogFragment.show(getSupportFragmentManager(), TAG);
    }
    public void AddTeammate() {
        dialogFragment = new AddTeammateFragment();
        dialogFragment.show(getSupportFragmentManager(), TAG);
    }
    /*
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
     */

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
        //int id_ = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.action_edit:

                Intent intent = new Intent(this, EditProject.class);
                intent.putExtra("id", id);
                startActivity(intent);
                return true;
            case R.id.action_delete_project:
                // Типо вызов диалога для подтверждения, но мне пока лень, поэтому чисто так
                DeleteProject deleteProject = new DeleteProject(id);
                deleteProject.execute();

                Intent intent1 = new Intent(this, Test.class);
                intent1.putExtra("item", 1);
                startActivity(intent1);
                return true;
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        if (!project.isCompleted()) {
            int drawable = getResources().getIdentifier("ic_baseline_check_24", "drawable", getPackageName());
            complete.setImageResource(drawable);
            complete.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.table_1_item_characteristics)));
        }
        else {
            int drawable = getResources().getIdentifier("ic_baseline_check_24", "drawable", getPackageName());
            complete.setImageResource(drawable);
            complete.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.floatingButton)));
        }
        boolean compl = project.isCompleted();
        SetCompleteProject setCompleteProject = new SetCompleteProject(id, project.isCompleted() == true ? 0 : 1);
        setCompleteProject.execute();
        project.setCompleted(compl);
    }

    @Override
    public void OnConfirmNegative() {

    }
}