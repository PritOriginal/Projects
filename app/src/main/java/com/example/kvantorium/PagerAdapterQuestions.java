package com.example.kvantorium;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;

public class PagerAdapterQuestions extends FragmentPagerAdapter {
    ArrayList<Question> questions;
    public PagerAdapterQuestions(FragmentManager fm, ArrayList<Question> questions) {
        super(fm);
        this.questions = questions;
    }

    @Override
    public Fragment getItem(int i) {
        QuestionFragment questionFragment = new QuestionFragment(questions.get(i));
        return questionFragment;
    }

    @Override
    public int getCount() {
        return questions.size();
    }
}
