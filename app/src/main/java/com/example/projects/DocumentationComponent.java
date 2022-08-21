package com.example.projects;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class DocumentationComponent extends Fragment implements OnComponentsListener{
    ComponentsInfo context;
    TextView documentation;
    Component component = new Component();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.documentation_component, container,
                false);
        documentation = (TextView)view.findViewById(R.id.component_documentation);
        if (context.component != null) {
            update();
        }
        return view;
    }

    public void update(){
        documentation.setText(context.component.getDocumentation().replace("[p]", "\n"));
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
