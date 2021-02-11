package com.example.projects;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.projects.server.GetAllProjectsUser;
import com.example.projects.server.GetUser;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements OnUserListener, OnProjectsListener {
    TextView name;
    TextView vk;
    TabLayout tabLayout;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    RecyclerView recyclerViewProjects;
    RVAdapterAchievement adapter;
    RVAdapter adapter_projects;
    List<Integer> achievements = new ArrayList<Integer>();
    List<Project> projects = new ArrayList<Project>();
    Project checkProject = new Project();
    int indexCheckProject;
    User user = new User();
    public int USER_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        USER_ID = getIntent().getExtras().getInt("id");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        name = (TextView)findViewById(R.id.profile_name);
        vk = (TextView)findViewById(R.id.profile_vk);
        recyclerView = (RecyclerView)findViewById(R.id.rvAchievement);
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(llm);
        achievements.add(0);
        achievements.add(1);
        adapter = new RVAdapterAchievement(this, achievements);
        recyclerView.setAdapter(adapter);
        GetUser getUser = new GetUser(this, this, USER_ID);
        getUser.execute();
        setProfile();

        recyclerViewProjects = (RecyclerView)findViewById(R.id.rv_projects_mini);
        LinearLayoutManager llm2 = new LinearLayoutManager(this);
        recyclerViewProjects.setLayoutManager(llm2);
        GetAllProjectsUser getAllProjectsUser = new GetAllProjectsUser(this,this,  USER_ID, progressBar);
        getAllProjectsUser.execute();
    }
    public void setProfile(){
        name.setText(user.getSecondName() + " " + user.getFirstName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        Boolean delete = data.getBooleanExtra("delete", false);
        if (!delete) {
            String name = data.getStringExtra("name");
            String description = data.getStringExtra("description");
            Boolean completed = data.getBooleanExtra("completed", false);
            checkProject.setName(name);
            checkProject.setDescription(description);
            checkProject.setCompleted(completed);
            projects.set(indexCheckProject, checkProject);
            adapter_projects.setProjects(projects);
        }
        else {
            projects.remove(indexCheckProject);
            adapter_projects.setProjects(projects);
        }
    }

    @Override
    public void onUserCompleted(User us) {
        user = us;
        user.setRaiting(100);
        name.setText(user.getSecondName() + " " + user.getFirstName());
        if (user.getVk() != "") {
            vk.setText(user.getVk());
            vk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/" + user.getVk()));
                    startActivity(browserIntent);
                }
            });
        }
        else {
            vk.setVisibility(View.GONE);
        }
    }

    @Override
    public void onUserError(String error) {

    }

    @Override
    public void onProjectsCompleted(ArrayList<Project> proj) {
        projects = proj;
        adapter_projects = new RVAdapter(this, projects,true,this);
        recyclerViewProjects.setAdapter(adapter_projects);
    }

    @Override
    public void onProjectsError(String error) {
    }

    @Override
    public void onProjectCheck(Project proj, int i) {
        checkProject = proj;
        indexCheckProject = i;
        Intent intent = new Intent(this, ProjectMentorActivity.class);
        intent.putExtra("id", checkProject.getId());
        intent.putExtra("idUser", USER_ID);
        startActivityForResult(intent, 1);
    }


}
