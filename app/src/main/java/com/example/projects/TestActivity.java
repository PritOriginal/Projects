package com.example.projects;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.projects.server.GetTest;
import com.example.projects.server.SendTest;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity implements OnTestsListener {
    Test test;
    TextView question;
    ViewPager viewPager;
    PagerAdapterQuestions pageAdapter;
    RadioGroup radioGroup;
    TabLayout tabLayout;
    FloatingActionButton next;
    ArrayList<Question> questions = new ArrayList<Question>();
    int indexQuestion = 0;
    public int id;
    public int USER_ID;
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
        radioGroup = (RadioGroup) findViewById(R.id.radio_group_answers);
        tabLayout = (TabLayout) findViewById(R.id.tabs_questions);
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
        if (viewPager.getCurrentItem() != pageAdapter.getCount() - 1) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        } else {
            SendTest sendTest = new SendTest(this, id, USER_ID, pageAdapter.getQuestions());
            sendTest.execute();

        }
    }

    @Override
    public void onTestsCompleted(ArrayList<Test> tests) {
        test = tests.get(0);
        questions = test.getQuestions();
        for (int i = 0; i < questions.size(); i++) {
            TabItem tabItem = new TabItem(this);
            //TabLayout.Tab tab = new TabLayout.Tab();
       //     tabLayout.addTab(tabLayout.newTab().);
        }
        pageAdapter = new PagerAdapterQuestions(getSupportFragmentManager(), questions, tabLayout);
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
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