package com.example.projects;

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

import com.example.projects.server.GetAllProjectsUser;

import java.util.ArrayList;
import java.util.List;

public class Profile extends Fragment implements OnUserListener, OnProjectsListener {

    Main main;

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
    Project checkProject = new Project();
    int indexCheckProject;

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
        recyclerView = (RecyclerView)view.findViewById(R.id.rvAchievement);
        LinearLayoutManager llm = new LinearLayoutManager(main.getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(llm);
        achievements.add(0);
        achievements.add(1);
        adapter = new RVAdapterAchievement(main, achievements);
        recyclerView.setAdapter(adapter);
        setProfile();
        System.out.println(main.user.getVk());
        if (main.user.getVk() != "") {
            vk.setText(main.user.getVk());
            vk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/" + main.user.getVk()));
                    startActivity(browserIntent);
                }
            });
        }
        else {
            vk.setVisibility(View.GONE);
        }
        recyclerViewProjects = (RecyclerView)view.findViewById(R.id.rv_projects_mini);
        LinearLayoutManager llm2 = new LinearLayoutManager(main.getApplicationContext());
        recyclerViewProjects.setLayoutManager(llm2);
        GetAllProjectsUser task = new GetAllProjectsUser(main,this,  main.USER_ID, progressBar);
        task.execute();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Main) {
            this.main = (Main) context;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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

    public void setProfile(){
        name.setText(main.user.getSecondName() + " " + main.user.getFirstName());
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
        adapter_projects = new RVAdapter(main, projects, false, this);
        recyclerViewProjects.setAdapter(adapter_projects);
    }

    @Override
    public void onProjectsError(String error) {
    }

    @Override
    public void onProjectCheck(Project proj, int i) {
        checkProject = proj;
        indexCheckProject = i;
        Intent intent = new Intent(main, ProjectActivity.class);
        intent.putExtra("id", checkProject.getId());
        intent.putExtra("idUser", main.USER_ID);
        startActivityForResult(intent, 1);
    }
}