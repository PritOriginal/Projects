package com.example.kvantorium;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class QuestionFragment extends Fragment {
    TestActivity context;
    Question question;
    TextView questionText;
    RadioGroup radioGroup;
    OnQuestionsListener mListener;
    int indexQuestion;

    public QuestionFragment(Question question, OnQuestionsListener mListener, int indexQuestion) {
        this.question = question;
        this.mListener = mListener;
        this.indexQuestion = indexQuestion;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_fragment, container,
                false);
        questionText = (TextView) view.findViewById(R.id.text_question);
        radioGroup = (RadioGroup) view.findViewById(R.id.radio_group_answers);
        questionText.setText(question.getQuestion());

        final ArrayList<Answer> answers = question.getAnswers();
        for (int i = 0; i < answers.size(); i++) {
            RadioButton radioButton = new RadioButton(context);
            radioButton.setText(answers.get(i).getAnswer());
            final int j = i;
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    question.setSelected(answers.get(j).getId());
                    mListener.onQuestionSetSelected(indexQuestion, question);
                }
            });
            radioGroup.addView(radioButton);
            radioButton.setChecked(answers.get(i).getId() == question.getSelected() ? true : false);

        }

        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof TestActivity) {
            this.context = (TestActivity) context;
        }
    }
}
