package com.example.kvantorium;

import java.util.ArrayList;

public interface OnMentorsListener {
    void onMentorsCompleted(ArrayList<Teammate> ment);
    void onMentorsError(String error);
}
