package com.example.kvantorium;

public interface OnUserListener {
    void onUserCompleted(User us);
    void onUserError(String error);
}
