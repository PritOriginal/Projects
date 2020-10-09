package com.example.kvantorium;

import java.util.ArrayList;

public interface OnMentorsListener {
    void onMentorsCompleted(ArrayList<User> us);
    void onMentorsError(String error);
}
