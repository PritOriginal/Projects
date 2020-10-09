package com.example.kvantorium;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kvantorium.server.GetStudents;

import java.util.ArrayList;
import java.util.List;

public class PeopleMentor extends Fragment implements OnUsersListener {

    Main2Activity main;

    RecyclerView recyclerView;
    RVAdapterUsers adapter;
    List<User> users = new ArrayList<User>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.people_mentor, container,
                false);

        recyclerView = (RecyclerView)view.findViewById(R.id.people_list);
        LinearLayoutManager llm = new LinearLayoutManager(main.getApplicationContext());
        recyclerView.setLayoutManager(llm);

        GetStudents getStudents = new GetStudents(this);
        getStudents.execute();

        return view;
    }

    public void update() {
        int group[] = new int[];
        ArrayList<ArrayList<User>> users_group = new ArrayList<ArrayList<User>>();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getGroup() != 0) {
                for (int j = 0; j < group.length; j++) {
                    if (group[j] == users.get(i).getGroup()) {
                   //     users_group.get()
                    }
                }
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Main2Activity) {
            this.main = (Main2Activity) context;
        }
    }

    @Override
    public void onUsersCompleted(ArrayList<User> us) {
        users = us;
        adapter = new RVAdapterUsers(main, users);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onUsersError(String error) {

    }
}
