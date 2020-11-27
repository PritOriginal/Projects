package com.example.kvantorium;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

import com.example.kvantorium.server.DeleteProject;
import com.example.kvantorium.server.GetProject;

public class ProjectActivity extends AppCompatActivity implements OnProjectListener, OnConfirmListener {
    TextView name;
    TextView description;
    public int USER_ID;
    DBHelper dbHelper;
    SQLiteDatabase database;
    Project project;
    TabLayout tabLayout;
    ViewPager viewPager;
    PageAdapter pageAdapter;
    DialogFragment dialogFragment;
    OnObjectiveListener ObjectiveListener;

    final String TAG = "dig1";

    public int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getExtras().getInt("id");
        setContentView(R.layout.activity_project);
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

        pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), this);
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
        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();
        USER_ID = getIntent().getExtras().getInt("idUser");

        GetProject task = new GetProject(this, id);
        task.execute();
    }

    public void setProject() {
    //    Project p = dbHelper.getProject(database, id);
        name.setText(project.getName());
        description.setText(project.getDescription());
    }

    public void ButtonAdd(View view) {
        System.out.println("page: " + tabLayout.getSelectedTabPosition());
        switch (tabLayout.getSelectedTabPosition()) {
            case 0:
                AddComponents();
                break;
            case 1:
                AddObjective();
                break;
            case 2:
                AddTeammate();
                break;
        }
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
                startActivityForResult(intent, 2);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        String name_ = data.getStringExtra("name");
        String description_ = data.getStringExtra("description");
        name.setText(name_);
        description.setText(description_);
        project.setName(name_);
        project.setDescription(description_);
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

    }

    @Override
    public void OnConfirmNegative() {

    }

    @Override
    public void OnConfirmDelete() {
        DeleteProject deleteProject = new DeleteProject(id);
        deleteProject.execute();
        //
        Intent intent1 = new Intent();
        intent1.putExtra("delete", true);
        setResult(RESULT_OK, intent1);
        this.finish();
    }
/*
    @Override
    public void onObjectiveCompleted(String objective) {
        System.out.println("objective: " + objective);
        AddObjective addObjective = new AddObjective(id, objective);
        addObjective.execute();
        ObjectivesProject objectivesProject = (ObjectivesProject) pageAdapter.getItem(1);
        RVAdapterObjective rvAdapterObjective = (RVAdapterObjective) objectivesProject.adapter;
        Objective o = new Objective(objective, false);
        updateObjectiveListener.onUpdateObjectiveCompleted(o);
    }

    @Override
    public void onObjectiveError(String error) {

    }
    */
}
