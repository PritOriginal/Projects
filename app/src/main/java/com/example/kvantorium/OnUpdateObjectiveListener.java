package com.example.kvantorium;

public interface OnUpdateObjectiveListener {
    void onUpdateObjectiveCompleted(Objective objective);
    void onUpdateObjectiveError(String error);
}
