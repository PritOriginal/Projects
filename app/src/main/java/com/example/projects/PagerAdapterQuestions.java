package com.example.projects;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class PagerAdapterQuestions extends FragmentPagerAdapter implements OnQuestionsListener {
    ArrayList<Question> questions;
    TabLayout tabLayout;
    private int numOfTabs;

    public PagerAdapterQuestions(FragmentManager fm, ArrayList<Question> questions, TabLayout tabLayout) {
        super(fm);
        this.questions = questions;
        this.tabLayout = tabLayout;
        numOfTabs = questions.size();
    }

    @Override
    public Fragment getItem(int i) {
        if (i < questions.size()) {
            QuestionFragment questionFragment = new QuestionFragment(questions.get(i), this, i);
            return questionFragment;
        } else {
            TestResultsFragment testResultsFragment = new TestResultsFragment();
            return testResultsFragment;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    @Override
    public void onQuestionSetSelected(int indexQuestion,Question question) {
        questions.set(indexQuestion, question);
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void finish() {
        numOfTabs = questions.size() + 1;
        notifyDataSetChanged();
    }

    public Fragment finishTest() {
        TestResultsFragment testResultsFragment = new TestResultsFragment();
        return testResultsFragment;
    }
}
