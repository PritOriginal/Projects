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

import com.example.projects.server.ChangeNumberComponentProject;
import com.example.projects.server.GetComponentsProject;

import java.util.ArrayList;
import java.util.List;

public class ComponentsProject extends Fragment implements OnComponentsListener{
    ProjectActivity context;
    RecyclerView recyclerView;
    RVAdapterComponentsProject adapter;
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
        GetComponentsProject task2 = new GetComponentsProject(context,this, context.id, progressBar);
        task2.execute();

        return view;
    }
    public void setComponents() {
        adapter = new RVAdapterComponentsProject(context, components, this);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
    public void update() {
        GetComponentsProject getComponentsProject = new GetComponentsProject(context,this, context.id, progressBar);
        getComponentsProject.execute();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof ProjectActivity) {
            this.context = (ProjectActivity) context;
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
        ChangeNumberComponentProject changeNumberComponentProject = new ChangeNumberComponentProject(context, c.getId(), number);
        changeNumberComponentProject.execute();
        c.setNumber(number);
        components.set(index, c);
        adapter.setComponents(components);
        adapter.notifyItemInserted(components.size());
    }

    @Override
    public void onComponentChangeNumberError(String error) {

    }
}
