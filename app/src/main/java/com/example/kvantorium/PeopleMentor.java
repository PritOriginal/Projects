package com.example.kvantorium;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kvantorium.server.GetStudents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PeopleMentor extends Fragment implements OnUsersListener {

    Main2Activity main;

    LinearLayout linearLayout;
    RVAdapterUsers adapter;
    List<User> users = new ArrayList<User>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.people_mentor, container,
                false);

        linearLayout = (LinearLayout)view.findViewById(R.id.people_list);

        GetStudents getStudents = new GetStudents(this);
        getStudents.execute();

        return view;
    }

    public void sorting() {
        ArrayList<Integer> group = new ArrayList<Integer>();
        ArrayList<ArrayList<User>> users_group = new ArrayList<ArrayList<User>>();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getGroup() != 0) {
                int j = 0;
                while (j < group.size()) {
                    if (group.get(j) == users.get(i).getGroup()) {
                        users_group.get(j).add(users.get(i));
                        break;
                    }
                    else if (j == group.size() - 1) {
                        group.add(users.get(i).getGroup());
                        ArrayList<User> users_ = new ArrayList<User>();
                        users_.add(users.get(i));
                        users_group.add(users_);
                    }
                    j++;
                }
                if (group.size() == 0) {
                    group.add(users.get(i).getGroup());
                    ArrayList<User> users_ = new ArrayList<User>();
                    users_.add(users.get(i));
                    users_group.add(users_);
                }
               //group.add(users.get(i).getGroup());
            }
        }
     //   System.out.println(users_group);
        update(group, users_group);
    }

    public void update(ArrayList<Integer> group, ArrayList<ArrayList<User>> users_group) {
        System.out.println(group.size() + "  и  " + users_group.size());
        for (int i = 0; i < users_group.size(); i++) {
            final View v1 = getLayoutInflater().inflate(R.layout.group_list, null);
            TextView t1 = (TextView) v1.findViewById(R.id.name_group);
            t1.setText("Группа " + group.get(i));
            RecyclerView groupList = (RecyclerView) v1.findViewById(R.id.group_list);
            LinearLayoutManager llm = new LinearLayoutManager(main.getApplicationContext());
            groupList.setLayoutManager(llm);
            adapter = new RVAdapterUsers(main, users_group.get(i));
            groupList.setAdapter(adapter);
            linearLayout.addView(v1);
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
       // adapter = new RVAdapterUsers(main, users);
       // recyclerView.setAdapter(adapter);
        sorting();
    }

    @Override
    public void onUsersError(String error) {

    }
}
