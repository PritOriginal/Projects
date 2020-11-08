package com.example.kvantorium;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kvantorium.server.GetAllProjectsUser;

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
    User user = new User();
    public int USER_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        //GetUser task = new GetUser(this, test.USER_ID);
        //task.execute();
        setProfile();

        vk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/prit_1"));
                startActivity(browserIntent);
            }
        });
        recyclerViewProjects = (RecyclerView)findViewById(R.id.rv_projects_mini);
        LinearLayoutManager llm2 = new LinearLayoutManager(this);
        recyclerViewProjects.setLayoutManager(llm2);
        GetAllProjectsUser task = new GetAllProjectsUser(this,  USER_ID, progressBar);
        task.execute();
    }
    public void setProfile(){
        name.setText(user.getSecondName() + " " + user.getFirstName());
    }

    @Override
    public void onUserCompleted(User us) {
        user = us;
        user.setRaiting(100);
        name.setText(user.getSecondName() + " " + user.getFirstName());
    }

    @Override
    public void onUserError(String error) {

    }

    @Override
    public void onProjectsCompleted(ArrayList<Project> proj) {
        projects = proj;
        adapter_projects = new RVAdapter(this, projects);
        recyclerViewProjects.setAdapter(adapter_projects);
//        progressBar.setVisibility(View.GONE);
        // recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProjectsError(String error) {
        //noneProject.setVisibility(View.VISIBLE);
//        progressBar.setVisibility(View.GONE);
    }
}
