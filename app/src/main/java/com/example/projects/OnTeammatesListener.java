package com.example.projects;

import java.util.ArrayList;

public interface OnTeammatesListener {
    void onTeammatesCompleted(ArrayList<User> teammates);
    void onTeammatesError(String error);
}
