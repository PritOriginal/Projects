package com.example.kvantorium;

import java.util.ArrayList;

public interface OnComponentsListener {
    void onComponentsCompleted(ArrayList<Component> components);
    void onComponentsError(String error);
    void onComponentChangeNumberCompleted(int number, int index, Component c);
    void onComponentChangeNumberError(String error);
}
