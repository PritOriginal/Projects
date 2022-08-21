package com.example.projects;

public interface OnUpdateObjectiveListener {
    void onUpdateObjectiveCompleted(Objective objective);
    void onUpdateObjectiveError(String error);
}
