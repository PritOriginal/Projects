package com.example.kvantorium;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class QRCodeFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.qr_code, null);

        LinearLayout qrLayout = (LinearLayout)view.findViewById(R.id.qrLayout);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        new Coder(QRCodeFragment.this, qrLayout).execute("Профиль:1");

        return builder.create();
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

}
