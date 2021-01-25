package com.example.kvantorium;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class QuestionFragment extends Fragment {
    Question question;
    TextView questionText;
    RadioGroup radioGroup;

    public QuestionFragment(Question question) {
        this.question = question;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_fragment, container,
                false);
        questionText = (TextView) view.findViewById(R.id.text_question);
        radioGroup = (RadioGroup) view.findViewById(R.id.radio_group_answers);

        return view;
    }
}
