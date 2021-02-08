package com.example.kvantorium;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.kvantorium.server.GetTestsUser;

import java.util.ArrayList;
import java.util.List;

public class TestsFragment extends Fragment implements OnTestsListener {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    RVAdapterTests adapter;
    List<Test> tests;
    Test checkTest = new Test();
    int indexCheckTest;

    Main main;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tests, container,
                false);
        recyclerView = (RecyclerView)view.findViewById(R.id.rv_tests);
        LinearLayoutManager llm = new LinearLayoutManager(main.getApplicationContext());
        recyclerView.setLayoutManager(llm);

        progressBar = (ProgressBar)view.findViewById(R.id.progressBarTests);
        progressBar.setProgress(0);

        GetTestsUser getTestsUser = new GetTestsUser(this, main.USER_ID, progressBar);
        getTestsUser.execute();
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
        checkTest = test;
        indexCheckTest = i;
        Intent intent = new Intent(main, TestActivity.class);
        intent.putExtra("id", checkTest.getId());
        intent.putExtra("idUser", main.USER_ID);
        startActivityForResult(intent, 1);
    }
}
