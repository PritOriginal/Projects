package com.example.kvantorium;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

@SuppressLint("ValidFragment")
public class ConfirmFragment extends DialogFragment implements DialogInterface.OnClickListener {
    OnConfirmListener mListener;
    int message;
    public ConfirmFragment(OnConfirmListener mListener, int message){
        this.mListener = mListener;
        this.message = message;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        switch (message) {
            case 0:
                builder.setTitle("Подтверждение")
                        .setMessage("Вы уверены, что хотите завершить проект?")
                        .setPositiveButton("Да", this)
                        .setNegativeButton("Нет", this);
                break;
            case 1:
                builder.setTitle("Подтверждение")
                        .setMessage("Вы уверены, что хотите отменить завершение проекта?")
                        .setPositiveButton("Да", this)
                        .setNegativeButton("Нет", this);
                break;
            case 2:
                builder.setTitle("Подтверждение")
                        .setMessage("Вы уверены, что хотите удалить проект?")
                        .setPositiveButton("Да", this)
                        .setNegativeButton("Нет", this);
                break;
        }
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                if (message != 2) {
                    mListener.OnConfirmPositive();
                }
                else {
                    mListener.OnConfirmDelete();
                }
                break;
            case Dialog.BUTTON_NEGATIVE:

                break;
        }

    }
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}
