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

import com.example.kvantorium.server.ChangeNumberComponentProject;
import com.example.kvantorium.server.GetComponentsProject;

import java.util.ArrayList;
import java.util.List;

public class ComponentsProjectMentor extends Fragment implements OnComponentsListener{
    ProjectMentorActivity context;
    RecyclerView recyclerView;
    RVAdapterComponents adapter;
    ProgressBar progressBar;
    List<Component> components = new ArrayList<Component>();
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.components_project, container,
                false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView_project);
        LinearLayoutManager llm = new LinearLayoutManager(context.getApplicationContext());
        recyclerView.setLayoutManager(llm);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBarComponentsProject);
        progressBar.setProgress(0);
        GetComponentsProject task2 = new GetComponentsProject(this, context.id, progressBar);
        task2.execute();

        return view;
    }
    public void setComponents() {
        //components = dbHelper.getComponentsProject(database, id);
        adapter = new RVAdapterComponents(context, components);
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
    public void onComponentsCompleted(ArrayList<Component> components) {
        this.components = components;
        setComponents();
    }

    @Override
    public void onComponentsError(String error) {

    }

    @Override
    public void onComponentChangeNumberCompleted(int number, int index, Component c) {

    }

    @Override
    public void onComponentChangeNumberError(String error) {

    }
}

