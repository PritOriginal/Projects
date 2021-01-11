package com.example.kvantorium;

import java.util.ArrayList;

public interface OnTestsListener {
    void onTestsCompleted(ArrayList<Test> tests);
    void onTestsError(String error);
}
