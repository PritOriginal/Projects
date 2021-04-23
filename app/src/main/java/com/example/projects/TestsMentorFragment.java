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

import com.example.projects.server.GetTestsUser;

import java.util.ArrayList;
import java.util.List;

public class TestsMentorFragment extends Fragment implements OnTestsListener {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    RVAdapterTests adapter;
    List<Test> tests;

    Main2Activity main;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tests_mentor, container,
                false);
        recyclerView = (RecyclerView)view.findViewById(R.id.rv_tests_mentor);
        LinearLayoutManager llm = new LinearLayoutManager(main.getApplicationContext());
        recyclerView.setLayoutManager(llm);

        progressBar = (ProgressBar)view.findViewById(R.id.progressBarTestMentor);
        progressBar.setProgress(0);
        GetTestsUser getTestsUser = new GetTestsUser(main,this,progressBar);
        getTestsUser.execute();
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Main2Activity) {
            this.main = (Main2Activity) context;
        }
    }
    @Override
    public void onTestsCompleted(ArrayList<Test> tests) {
        this.tests = tests;
        adapter = new RVAdapterTests(main, tests, this);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTestsError(String error) {

    }

    @Override
    public void onTestCheck(Test test, int i) {

    }
}
