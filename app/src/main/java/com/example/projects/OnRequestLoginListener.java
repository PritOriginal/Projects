package com.example.projects;

import java.util.ArrayList;

public interface OnRequestLoginListener {
    void onRequestCompleted(ArrayList<String> req);
    void onComponentsError(String error);
}
