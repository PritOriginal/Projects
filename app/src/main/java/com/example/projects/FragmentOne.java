package com.example.projects;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.projects.server.GetAllProjectsUser;

import java.util.ArrayList;
import java.util.List;

public class FragmentOne extends Fragment implements View.OnClickListener, OnProjectsListener {
    FloatingActionButton create;
    DBHelper dbHelper;
    SQLiteDatabase database;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    RVAdapter adapter;
    List<Project> projects = new ArrayList<Project>();
    Project checkProject = new Project();
    int indexCheckProject;
    public int USER_ID = 0;
    private int countID;

    Main main;

    public FragmentOne() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container,
                false);

        recyclerView = (RecyclerView)view.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(main.getApplicationContext());
        recyclerView.setLayoutManager(llm);
        //ContentValues P = new ContentValues();
        dbHelper = new DBHelper(main);
        //P.put("name,");
        create = (FloatingActionButton)view.findViewById(R.id.create);
        create.setOnClickListener(main);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBarProjects);
        progressBar.setProgress(0);
        database = dbHelper.getWritableDatabase();

        GetAllProjectsUser task = new GetAllProjectsUser(main,this, main.USER_ID, progressBar);
        task.execute();
        return view;
    }
    public void update() {
        projects = dbHelper.getAllProjectUser(database, USER_ID);
        adapter = new RVAdapter(main, projects, false, this);
        recyclerView.setAdapter(adapter);
        dbHelper.close();
    }

    @Override
    public void onClick(View v) {
        ContentValues contentValues = new ContentValues();

        switch (v.getId()) {
            case R.id.create:
                Intent intent = new Intent(main, CreateProject.class);
                intent.putExtra("idUser", main.USER_ID);
                startActivity(intent);
                break;
        }
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
            adapter.setProjects(projects);
        }
        else {
            projects.remove(indexCheckProject);
            adapter.setProjects(projects);
        }
    }

    @Override
    public void onProjectsCompleted(ArrayList<Project> proj) {
        projects = proj;
        adapter = new RVAdapter(main, projects, false, this);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProjectsError(String error) {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onProjectCheck(Project proj, int i) {
        checkProject = proj;
        indexCheckProject = i;
        Intent intent = new Intent(main, ProjectActivity.class);
        intent.putExtra("id", checkProject.getId());
        intent.putExtra("idUser", USER_ID);
        startActivityForResult(intent, 1);
    }
}
