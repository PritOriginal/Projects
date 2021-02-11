package com.example.projects;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.projects.server.GetTeammatesProject;

import java.util.ArrayList;
import java.util.List;

public class TeammatesProjectMentor extends Fragment implements OnTeammatesListener {
    ProjectMentorActivity context;
    RecyclerView recyclerView;
    RVAdapterUsers adapter;
    ProgressBar progressBar;
    List<User> teammates = new ArrayList<User>();
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teammates_project, container,
                false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recylerViewTeammates);
        LinearLayoutManager llm = new LinearLayoutManager(context.getApplicationContext());
        recyclerView.setLayoutManager(llm);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBarTeammatesProject);
        progressBar.setProgress(0);
        updateTeammates();
        return view;
    }

    public void updateTeammates(){
        GetTeammatesProject getTeammatesProject = new GetTeammatesProject(context, this, context.id, progressBar);
        getTeammatesProject.execute();
    }

    public void setTeammates(){
        adapter = new RVAdapterUsers(context, teammates);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof ProjectMentorActivity) {
            this.context = (ProjectMentorActivity) context;
        }
    }

    @Override
    public void onTeammatesCompleted(ArrayList<User> teammates) {
        this.teammates = teammates;
        setTeammates();
    }

    @Override
    public void onTeammatesError(String error) {

    }
}

