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

import com.example.projects.server.AddObjective;
import com.example.projects.server.ChangeObjective;
import com.example.projects.server.GetObjectivesProject;

import java.util.ArrayList;
import java.util.List;

public class ObjectivesProject extends Fragment implements OnObjectiveListener {
    ProjectActivity context;
    RecyclerView recyclerView;
    RVAdapterObjective adapter;
    ProgressBar progressBar;
    List<Objective> objectives = new ArrayList<Objective>();
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.objectives_project, container,
                false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recylerViewObjecives);
        LinearLayoutManager llm = new LinearLayoutManager(context.getApplicationContext());
        recyclerView.setLayoutManager(llm);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBarObjectivesProject);
        progressBar.setProgress(0);
        updateObjectives();
        return view;
    }

    public void updateObjectives() {
        GetObjectivesProject task = new GetObjectivesProject(context,this, context.id, progressBar);
        task.execute();
    }

    public void setObjectives() {
        adapter = new RVAdapterObjective(context, objectives, this, false);
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
    public void onObjectivesCompleted(ArrayList<Objective> objectives) {
        this.objectives = objectives;
        setObjectives();
    }

    @Override
    public void onObjectivesError(String error) {

    }

    @Override
    public void onCreateObjectiveCompleted(String objective) {
        AddObjective addObjective = new AddObjective(context, context.id, objective);
        addObjective.execute();
        objectives.add(new Objective(objective, false));
        adapter.notifyItemInserted(objectives.size());
    }

    @Override
    public void onCreateObjectiveError(String error) {

    }

    @Override
    public void onChangeObjectiveCompleted(String objective, int index, Objective o) {
        ChangeObjective changeObjective = new ChangeObjective(context, o.getId(), objective);
        changeObjective.execute();
        o.setObjective(objective);
        objectives.set(index, o);
        adapter.setObjectives(objectives);
        adapter.notifyItemInserted(objectives.size());
    }

    @Override
    public void onChangeObjectiveError(String error) {

    }

    public OnObjectiveListener getCreateListener(){
        return this;
    }
}
