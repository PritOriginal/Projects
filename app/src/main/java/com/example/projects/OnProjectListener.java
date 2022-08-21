package com.example.projects;

public interface OnProjectListener {
    void onProjectCompleted(Project proj);
    void onProjectError(String error);
}
