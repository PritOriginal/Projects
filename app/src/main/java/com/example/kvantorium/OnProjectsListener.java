package com.example.kvantorium;

import java.util.ArrayList;

public interface OnProjectsListener {
    void onProjectsCompleted(ArrayList<Project> proj);
    void onProjectsError(String error);
    void onProjectCheck(Project proj, int i);

}
