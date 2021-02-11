package com.example.projects;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projects.server.GetComponentByComponentProject;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class ChangeNumberComponentProjectFragment extends DialogFragment implements DialogInterface.OnClickListener, OnComponentsListener {
    Context mContext;
    OnComponentsListener mListener;
    int index;
    Component component;

    ImageView image;
    TextView name;
    TextView number;
    EditText editNumber;

    public ChangeNumberComponentProjectFragment(Context mContext, OnComponentsListener mListener, int index, Component component){
        this.mContext = mContext;
        this.mListener = mListener;
        this.index = index;
        this.component = component;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.change_number_component, null);
        view.findViewById(R.id.image_change_component);
        image = (ImageView)view.findViewById(R.id.image_change_component);
        if (component.getImage() != null) {
            int drawable = mContext.getResources().getIdentifier(component.getImage(), "drawable", mContext.getPackageName());
            image.setImageResource(drawable);
        }
        name = (TextView)view.findViewById(R.id.name_change_component);
        name.setText(component.getNameComponent());
        number = (TextView)view.findViewById(R.id.number_change_component);
        GetComponentByComponentProject getComponent = new GetComponentByComponentProject(mContext,this, component.getId());
        getComponent.execute();
        editNumber = (EditText)view.findViewById(R.id.edit_number_change_component);
        editNumber.setText(String.valueOf(component.getNumber()));


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setTitle("Компонент")
                .setPositiveButton("Изменить", this)
                .setNegativeButton("Отмена", this);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                mListener.onComponentChangeNumberCompleted(Integer.valueOf(editNumber.getText().toString()), index, component);
                break;
            case Dialog.BUTTON_NEGATIVE:

                break;
        }
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

    @Override
    public void onComponentsCompleted(ArrayList<Component> components) {
        number.setHint("шт. " +String.valueOf(components.get(0).getNumber()));
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
