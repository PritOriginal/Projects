package com.example.projects;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class ProfileMiniFragment extends DialogFragment {
    TextView name_user;
    TextView vk_user;
    String name;
    String vk;

    public ProfileMiniFragment(String name, String vk) {
        this.name = name;
        this.vk = vk;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.profile, null);
        name_user = (TextView)view.findViewById(R.id.profile_name);
        vk_user = (TextView)view.findViewById(R.id.profile_vk);
        name_user.setText(name);
        if (vk != "") {
            vk_user.setText(vk);
            vk_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/" + vk));
                    startActivity(browserIntent);
                }
            });
        }
        else {
            vk_user.setVisibility(View.GONE);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        return builder.create();
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}
