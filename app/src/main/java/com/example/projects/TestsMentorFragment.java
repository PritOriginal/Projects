package com.example.projects;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class TestsMentorFragment extends Fragment implements OnTestsListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tests, container,
                false);

        return view;
    }
    @Override
    public void onTestsCompleted(ArrayList<Test> tests) {

    }

    @Override
    public void onTestsError(String error) {

    }

    @Override
    public void onTestCheck(Test test, int i) {

    }
}
