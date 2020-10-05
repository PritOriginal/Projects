package com.example.kvantorium;

import java.util.ArrayList;

public interface OnTeammatesListener {
    void onTeammatesCompleted(ArrayList<Teammate> teammates);
    void onTeammatesError(String error);
}
