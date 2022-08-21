package com.example.projects;

import java.util.ArrayList;

public interface OnObjectiveListener {
    void onObjectivesCompleted(ArrayList<Objective> objectives);
    void onObjectivesError(String error);
    void onCreateObjectiveCompleted(String objective);
    void onCreateObjectiveError(String error);
    void onChangeObjectiveCompleted(String objective, int index, Objective o);
    void onChangeObjectiveError(String error);
}
