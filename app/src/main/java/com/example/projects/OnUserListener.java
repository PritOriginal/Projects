package com.example.projects;

public interface OnUserListener {
    void onUserCompleted(User us);
    void onUserError(String error);
}
