package com.example.kvantorium;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kvantorium.server.GetTest;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity implements OnTestsListener {
    Test test;
    public int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getExtras().getInt("id");
        setContentView(R.layout.activity_test);
        GetTest getTest = new GetTest(this, id);
        getTest.execute();
    }

    public void setTest(){

    }

    @Override
    public void onTestsCompleted(ArrayList<Test> tests) {
        test = tests.get(0);
    }

    @Override
    public void onTestsError(String error) {

    }

    @Override
    public void onTestCheck(Test test, int i) {

    }
}