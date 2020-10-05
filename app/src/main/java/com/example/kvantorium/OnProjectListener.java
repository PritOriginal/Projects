package com.example.kvantorium;

public interface OnProjectListener {
    void onProjectCompleted(Project proj);
    void onProjectError(String error);
}
