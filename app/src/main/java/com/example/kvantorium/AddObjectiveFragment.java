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
public class AddObjectiveFragment extends DialogFragment  implements DialogInterface.OnClickListener {

    OnObjectiveListener mListener;

    EditText objective;
    public AddObjectiveFragment (OnObjectiveListener mListener) {
        this.mListener = mListener;
    }

    /*
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Цель");
        View v = inflater.inflate(R.layout.add_objective, null);
        v.findViewById(R.id.input_text);
        v.findViewById(R.id.createObjective);
        v.findViewById(R.id.cancel);
        return v;
    }
*/
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_objective, null);
        view.findViewById(R.id.input_text);
        objective = (EditText)view.findViewById(R.id.input_text);
        //view.findViewById(R.id.createObjective);
        //view.findViewById(R.id.cancel);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setTitle("Задача")
                .setPositiveButton("Создать", this)
                .setNegativeButton("Отмена", this);
                /*
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //отправляем результат обратно
                        Intent intent = new Intent();
                        intent.putExtra(TAG_WEIGHT_SELECTED, mNpWeight.getValue());
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                    }
                });
        */
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
