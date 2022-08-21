package com.example.projects;

import java.util.ArrayList;

public interface OnTestsListener {
    void onTestsCompleted(ArrayList<Test> tests);
    void onTestsError(String error);
    void onTestCheck(Test test, int i);
}
