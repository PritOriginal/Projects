package com.example.projects;

import java.util.ArrayList;

public interface OnUsersListener {
    void onUsersCompleted(ArrayList<User> us);
    void onUsersError(String error);
}
