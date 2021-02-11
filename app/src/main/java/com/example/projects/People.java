package com.example.projects;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.projects.server.GetMentors;

import java.util.ArrayList;
import java.util.List;

public class People extends Fragment implements OnMentorsListener {
    LinearLayout mentorsLiner;
    ImageView mentorsImage;
    FrameLayout frameLayout;
    RecyclerView recyclerView;
    RVAdapterUsers adapter;
    List<User> mentors = new ArrayList<User>();

    boolean mentorsVisible = true;
    Main main;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.actvity_people, container,
                false);


        mentorsLiner = (LinearLayout)view.findViewById(R.id.mentors);
        mentorsLiner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMentors();
            }
        });
        mentorsImage = (ImageView)view.findViewById(R.id.mentorsImage);
        frameLayout = (FrameLayout)view.findViewById(R.id.FLMentors);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewMentors);
        LinearLayoutManager llm = new LinearLayoutManager(main.getApplicationContext());
        recyclerView.setLayoutManager(llm);

        GetMentors getMentors = new GetMentors(main,this);
        getMentors.execute();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Main) {
            this.main = (Main) context;
        }
    }

    public void showMentors() {
        if (mentorsVisible) {
            frameLayout.setVisibility(View.GONE);
            mentorsImage.setImageResource(R.drawable.ic_expand_more_black_24dp);
            Animation close = AnimationUtils.loadAnimation(main, R.anim.scroll_list_close);
            frameLayout.startAnimation(close);
            mentorsVisible = !mentorsVisible;
        } else {
            frameLayout.setVisibility(View.VISIBLE);
            mentorsImage.setImageResource(R.drawable.ic_expand_less_black_24dp);
            Animation open = AnimationUtils.loadAnimation(main, R.anim.scroll_list_open);
            frameLayout.startAnimation(open);
            mentorsVisible = !mentorsVisible;
        }
    }

    @Override
    public void onMentorsCompleted(ArrayList<User> us) {
        mentors = us;
        adapter = new RVAdapterUsers(main, mentors);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onMentorsError(String error) {

    }
}
