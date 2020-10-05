package com.example.kvantorium;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kvantorium.server.GetComponentByComponentProject;

import java.util.ArrayList;

public class DescriptionComponent extends Fragment implements OnComponentsListener {
    ComponentsInfo context;
    TextView name;
    ImageView image;
    TextView description;
    Component component = new Component();
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.description_component, container,
                false);
        name = (TextView)view.findViewById(R.id.title_component_name);
        image = (ImageView)view.findViewById(R.id.title_component_image);
        description = (TextView)view.findViewById(R.id.component_description);
        if (context.component != null) {
            update();
        }
        return view;
    }


    public void update(){
        System.out.println(component.getNameComponent() +"\n"+ component.getUseNumber() +"\n"+ component.getNumber() +"\n"+ component.getImage() +"\n"+ component.getDescription() +"\n"+ component.getCharacteristics() +"\n"+ component.getDocumentation());
        name.setText(component.getNameComponent());
        if (component.getImage() != null) {
            int drawable = context.getApplicationContext().getResources().getIdentifier(component.getImage(), "drawable", context.getApplicationContext().getPackageName());
            image.setImageResource(drawable);
        }
        description.setText(component.getDescription().replace("[p]", "\n"));
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
