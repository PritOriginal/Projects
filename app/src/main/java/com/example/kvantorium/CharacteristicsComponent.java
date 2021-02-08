package com.example.kvantorium;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.util.Linkify;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CharacteristicsComponent extends Fragment implements OnComponentsListener {
    ComponentsInfo context;
    LinearLayout linearLayout;
    Component component = new Component();
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.characteristics_component, container,
                false);
        linearLayout = (LinearLayout)view.findViewById(R.id.linearCharacteristics);
        return view;
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void update(){
        String characteristics = component.getCharacteristics();
        ArrayList<String> first = new ArrayList<String>();
        ArrayList<String> second = new ArrayList<String>();
        StringBuilder s = new StringBuilder();
        int j = 0;
        for (int i = 0; i < characteristics.length(); i++){
            if (characteristics.charAt(i) != '|') {
                s.append(characteristics.charAt(i));
            }
            else {
                if (j == 0) {
                    first.add(s.toString());
                    s = new StringBuilder();
                    j = 1;
                }
                else {
                    second.add(s.toString());
                    s = new StringBuilder();
                    j = 0;
                }
            }
        }

        for (int i = 0; i < first.size(); i++) {
            final View v1 = getLayoutInflater().inflate(R.layout.item_characteristics_1, null);
            TextView t1 = (TextView)v1.findViewById(R.id.characteristics_item_1);
            t1.setText(first.get(i) + ":");
            linearLayout.addView(v1);
            final View v2 = getLayoutInflater().inflate(R.layout.item_characteristics_2, null);
            TextView t2 = (TextView)v2.findViewById(R.id.characteristics_item_2);
            t2.setText(second.get(i));
            linearLayout.addView(v2);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof ComponentsInfo) {
            this.context = (ComponentsInfo) context;
        }
    }

    public OnComponentsListener getListener(){
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onComponentsCompleted(ArrayList<Component> components) {
        component = components.get(0);
        update();
    }

    @Override
    public void onComponentsError(String error) {

    }

    @Override
    public void onComponentChangeNumberCompleted(int number, int index, Component c) {

    }

    @Override
    public void onComponentChangeNumberError(String error) {

    }
}
