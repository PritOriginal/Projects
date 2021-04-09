package com.example.projects;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.projects.server.GetTest;
import com.example.projects.server.SendTest;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity implements OnTestsListener {
    public Test test;
    TextView question;
    TextView number_questions;
    ViewPager viewPager;
    PagerAdapterQuestions pageAdapter;
    RadioGroup radioGroup;
    TabLayout tabLayout;
    ProgressBar progressBar;
    FloatingActionButton next;
    ArrayList<Question> questions = new ArrayList<Question>();
    int indexQuestion = 1;
    int indexTab = 0;
    int results;
    public int id;
    public int USER_ID;
    boolean finishTest = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getExtras().getInt("id");
        USER_ID = getIntent().getExtras().getInt("idUser");
        setContentView(R.layout.activity_test);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_test);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        viewPager = (ViewPager)findViewById(R.id.viewPagerQuestions);

        question = (TextView) findViewById(R.id.text_question);
        number_questions = (TextView) findViewById(R.id.number_questions);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group_answers);
        tabLayout = (TabLayout) findViewById(R.id.tabs_questions);
        progressBar = (ProgressBar) findViewById(R.id.progressTest);
        next = (FloatingActionButton) findViewById(R.id.buttonNextQuestion);
        GetTest getTest = new GetTest(this, this, id);
        getTest.execute();
    }
/*
    public void setTest(){
        ArrayList<Question> questions = test.getQuestions();
        question.setText(questions.get(indexQuestion).getQuestion());
        ArrayList<Answer> answers = questions.get(indexQuestion).getAnswers();
        for (int i = 0; i < answers.size(); i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(answers.get(i).getAnswer());

            //final View v = getLayoutInflater().inflate(R.layout.answer_radio_button, null);
            //RadioButton radioButtonAnswer = (RadioButton)v.findViewById(R.id.radioButtonAnswer);
            //radioButtonAnswer.setText(answers.get(i).getAnswer());
            radioGroup.addView(radioButton);
        }
    }
 */
    public void NextQuestion(View view) {
        if (viewPager.getCurrentItem() < pageAdapter.getCount() - 1) {
            indexQuestion++;
            number_questions.setText(String.valueOf(indexQuestion) + '/' +questions.size());
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            System.out.println((int) (progressBar.getProgress() + 1f/questions.size()*1000));
            ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar,"progress", progressBar.getProgress(), (int) (progressBar.getProgress() + 1f/questions.size()*1000));
            progressAnimator.setDuration(150);
            progressAnimator.setInterpolator(new LinearInterpolator());
            progressAnimator.start();
           // progressBar.setProgress((int)(progressBar.getProgress() + 1f/questions.size()*1000));
        } else if (viewPager.getCurrentItem() == pageAdapter.getCount() - 1 & !finishTest) {
            pageAdapter.finish();
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            System.out.println((int) (progressBar.getProgress() + 1f/questions.size()*1000));
            ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar,"progress", progressBar.getProgress(), (int) (progressBar.getProgress() + 1f/questions.size()*1000));
            progressAnimator.setDuration(150);
            progressAnimator.setInterpolator(new LinearInterpolator());
            progressAnimator.start();
            finishTest = true;
            SendTest sendTest = new SendTest(this, id, USER_ID, pageAdapter.getQuestions());
            sendTest.execute();
        } else {
            Intent intent = new Intent();
            intent.putExtra("completed", true);
            intent.putExtra("results", results);
            setResult(RESULT_OK, intent);
            this.finish();
        }
    }

    @Override
    public void onTestsCompleted(ArrayList<Test> tests) {
        test = tests.get(0);
        questions = test.getQuestions();
        number_questions.setText(String.valueOf(indexQuestion) + '/' +questions.size());
        for (int i = 0; i < questions.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(String.valueOf(i+1)));
        }
        progressBar.setProgress(1/questions.size());
        pageAdapter = new PagerAdapterQuestions(getSupportFragmentManager(), questions, tabLayout);
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar,"progress", progressBar.getProgress(), (int) (progressBar.getProgress() + 1f/questions.size()*1000*(tab.getPosition()-indexTab)));
                progressAnimator.setDuration(150);
                progressAnimator.setInterpolator(new LinearInterpolator());
                progressAnimator.start();
                indexTab = tab.getPosition();
                indexQuestion = indexTab + 1;
                number_questions.setText(String.valueOf(indexQuestion) + '/' + questions.size());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onTestsError(String error) {

    }

    @Override
    public void onTestCheck(Test test, int i) {

    }
}