package com.example.projects;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class PagerAdapterQuestions extends FragmentPagerAdapter implements OnQuestionsListener {
    ArrayList<Question> questions;
    TabLayout tabLayout;
    public PagerAdapterQuestions(FragmentManager fm, ArrayList<Question> questions, TabLayout tabLayout) {
        super(fm);
        this.questions = questions;
        this.tabLayout = tabLayout;
    }

    @Override
    public Fragment getItem(int i) {
        QuestionFragment questionFragment = new QuestionFragment(questions.get(i), this, i);
        return questionFragment;
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public void onQuestionSetSelected(int indexQuestion,Question question) {
        questions.set(indexQuestion, question);
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }
}
