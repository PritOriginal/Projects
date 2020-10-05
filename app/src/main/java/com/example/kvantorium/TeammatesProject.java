package com.example.kvantorium;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.kvantorium.server.GetTeammatesProject;

import java.util.ArrayList;
import java.util.List;

public class TeammatesProject extends Fragment implements OnTeammatesListener {
    ProjectActivity context;
    RecyclerView recyclerView;
    RVAdapterTeammates adapter;
    ProgressBar progressBar;
    List<Teammate> teammates = new ArrayList<Teammate>();
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
        GetTeammatesProject getTeammatesProject = new GetTeammatesProject(this, context.id, progressBar);
        getTeammatesProject.execute();
    }

    public void setTeammates(){
        adapter = new RVAdapterTeammates(context, teammates);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof ProjectActivity) {
            this.context = (ProjectActivity) context;
        }
    }

    @Override
    public void onTeammatesCompleted(ArrayList<Teammate> teammates) {
        this.teammates = teammates;
        setTeammates();
    }

    @Override
    public void onTeammatesError(String error) {

    }
}
