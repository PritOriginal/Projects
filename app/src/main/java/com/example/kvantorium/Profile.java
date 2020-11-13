package com.example.kvantorium;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kvantorium.server.GetAllProjectsUser;

import java.util.ArrayList;
import java.util.List;

public class Profile extends Fragment implements OnUserListener, OnProjectsListener {

    Test test;

    TextView name;
    TextView vk;
    TextView raiting;

    ProgressBar progressBar;
    RecyclerView recyclerView;
    RecyclerView recyclerViewProjects;
    RVAdapterAchievement adapter;
    RVAdapter adapter_projects;
    List<Integer> achievements = new ArrayList<Integer>();
    List<Project> projects = new ArrayList<Project>();
    User user = new User();

    public Profile() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container,
                false);
        name = (TextView) view.findViewById(R.id.profile_name);
        vk = (TextView) view.findViewById(R.id.profile_vk);
        raiting = (TextView) view.findViewById(R.id.raiting);
        //r = test.getIntent().getExtras().getInt("raiting");
        //name.setText("Махнатеев Степан");

        recyclerView = (RecyclerView)view.findViewById(R.id.rvAchievement);
        LinearLayoutManager llm = new LinearLayoutManager(test.getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(llm);
        achievements.add(0);
        achievements.add(1);
        adapter = new RVAdapterAchievement(test, achievements);
        recyclerView.setAdapter(adapter);
        //GetUser task = new GetUser(this, test.USER_ID);
        //task.execute();
        setProfile();
        System.out.println(test.user.getVk());
        if (test.user.getVk() != "") {
            vk.setText(test.user.getVk());
            vk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/" + test.user.getVk()));
                    startActivity(browserIntent);
                }
            });
        }
        else {
            vk.setVisibility(View.GONE);
        }
        recyclerViewProjects = (RecyclerView)view.findViewById(R.id.rv_projects_mini);
        LinearLayoutManager llm2 = new LinearLayoutManager(test.getApplicationContext());
        recyclerViewProjects.setLayoutManager(llm2);
        GetAllProjectsUser task = new GetAllProjectsUser(this,  test.USER_ID, progressBar);
        task.execute();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Test) {
            this.test = (Test) context;
        }
    }

    public void setProfile(){
        name.setText(test.user.getSecondName() + " " + test.user.getFirstName());
    }


    @Override
    public void onUserCompleted(User us) {
        user = us;
        user.setRaiting(100);
        name.setText(user.getSecondName() + " " + user.getFirstName());
        raiting.setText(String.valueOf(user.getRaiting()));
    }

    @Override
    public void onUserError(String error) {

    }

    @Override
    public void onProjectsCompleted(ArrayList<Project> proj) {
        projects = proj;
        adapter_projects = new RVAdapter(test, projects, false);
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