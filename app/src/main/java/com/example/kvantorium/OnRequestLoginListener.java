package com.example.kvantorium;

import java.util.ArrayList;
import java.util.HashMap;

public interface OnRequestLoginListener {
    void onRequestCompleted(ArrayList<String> req);
    void onComponentsError(String error);
}
