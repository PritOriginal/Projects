package com.example.kvantorium;

import android.os.AsyncTask;

import java.util.ArrayList;

public class TestTask extends AsyncTask<Void, Void, ArrayList<Component>> {
    //initiate vars
    private OnComponentsListener mListener;
    ArrayList<Component> components = new ArrayList<Component>();

    public TestTask(OnComponentsListener listener) {
        mListener = listener;
    }

    protected ArrayList<Component> doInBackground(Void... params) {
        //do stuff
        Component c = new Component();
        c.setNameComponent("Arduino");
        c.setNumber(30);
        components.add(c);
        return components;
    }

    @Override
    protected void onPostExecute(ArrayList<Component> components) {
        //do stuff
        //super.onPostExecute(components);
        if (mListener != null) {
            mListener.onComponentsCompleted(components);
        }
    }
}
