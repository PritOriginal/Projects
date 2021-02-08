package com.example.kvantorium;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.kvantorium.server.AddObjective;

@SuppressLint("ValidFragment")
public class AddObjectiveFragment extends DialogFragment implements DialogInterface.OnClickListener {

    OnObjectiveListener mListener;

    EditText objective;
    public AddObjectiveFragment (OnObjectiveListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_objective, null);
        view.findViewById(R.id.input_text);
        objective = (EditText)view.findViewById(R.id.input_text);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setTitle("Задача")
                .setPositiveButton("Создать", this)
                .setNegativeButton("Отмена", this);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                mListener.onCreateObjectiveCompleted(objective.getText().toString());
                break;
            case Dialog.BUTTON_NEGATIVE:

                break;
        }
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}
