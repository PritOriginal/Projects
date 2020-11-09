package com.example.kvantorium;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProfileMentor extends Fragment implements OnUserListener {

    Main2Activity main;

    TextView name;
    TextView vk;
    TextView raiting;

    RecyclerView recyclerView;
    RVAdapterAchievement adapter;
    List<Integer> achievements = new ArrayList<Integer>();
    User user = new User();

    public ProfileMentor() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_mentor_fragment, container,
                false);
        name = (TextView) view.findViewById(R.id.profile_name);
        vk = (TextView) view.findViewById(R.id.profile_vk);
        raiting = (TextView) view.findViewById(R.id.raiting);
        //r = test.getIntent().getExtras().getInt("raiting");
        //name.setText("Махнатеев Степан");

        recyclerView = (RecyclerView)view.findViewById(R.id.rvAchievement);
        LinearLayoutManager llm = new LinearLayoutManager(main.getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(llm);
        achievements.add(0);
        achievements.add(1);
        adapter = new RVAdapterAchievement(main, achievements);
        recyclerView.setAdapter(adapter);
        setProfile();
        if (user.getVk() != "") {
            vk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/" + user.getVk()));
                    startActivity(browserIntent);
                }
            });
        }
        else {
            vk.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Main2Activity) {
            this.main = (Main2Activity) context;
        }
    }

    public void setProfile(){
        name.setText(main.user.getSecondName() + " " + main.user.getFirstName());
    }

    @Override
    public void onUserCompleted(User us) {
        user = us;
        user.setRaiting(100);
        name.setText(user.getSecondName() + " " + user.getFirstName());
        raiting.setText(String.valueOf(user.getRaiting()));
    }

    @Override
    public void onUserError(String error) {

    }

}
