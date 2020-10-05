package com.example.kvantorium;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kvantorium.server.GetAllProjectsUser;
import com.example.kvantorium.server.GetUser;

import java.util.ArrayList;
import java.util.List;

public class Profile extends Fragment implements OnUserListener {

    Test test;

    TextView name;
    TextView vk;
    TextView raiting;
    DBHelper dbHelper;

    SQLiteDatabase database;
    RecyclerView recyclerView;
    RVAdapterAchievement adapter;
    List<Integer> achievements = new ArrayList<Integer>();
    List<Project> projects = new ArrayList<Project>();
    User user = new User();

    public int USER_ID = 0;


    int r;

    public Profile() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container,
                false);
        name = (TextView) view.findViewById(R.id.profile_name);
        vk = (TextView) view.findViewById(R.id.profile_vk);
        raiting = (TextView) view.findViewById(R.id.raiting);
        //r = test.getIntent().getExtras().getInt("raiting");
        //name.setText("Махнатеев Степан");

        recyclerView = (RecyclerView)view.findViewById(R.id.rvAchievement);
        LinearLayoutManager llm = new LinearLayoutManager(test.getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(llm);
        achievements.add(0);
        achievements.add(1);
        adapter = new RVAdapterAchievement(test, achievements);
        recyclerView.setAdapter(adapter);
        GetUser task = new GetUser(this, test.USER_ID);
        task.execute();

        vk.setText(Html.fromHtml("<a href='https://vk.com/prit_1'>prit_1</a>"));
        vk.setMovementMethod(LinkMovementMethod.getInstance());
        /*
        vk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("<a href=\"https://vk.com/" + vk.getText().toString() + "\"" + ">"+ vk.getText().toString() +"</a>"));
                v.getContext().startActivity(i);
            }
        });
        */

        return view;
    }

    public void update() {
     //   projects = dbHelper.getAllProjectUser(database, USER_ID);
      //  adapter = new RVAdapterProjectsMini(test, projects, database);
      //  recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Test) {
            this.test = (Test) context;
        }
    }


    @Override
    public void onUserCompleted(User us) {
        user = us;
        user.setRaiting(100);
        name.setText(user.getSecondname() + " " + user.getFirstName());
        raiting.setText(String.valueOf(user.getRaiting()));
    }

    @Override
    public void onUserError(String error) {

    }
}