package com.example.projects;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TestResultsFragment extends Fragment {
    TestActivity context;
    RecyclerView recyclerView;
    ArrayList<Question> results = new ArrayList<Question>();
    RVAdapterTestResults adapter;
    TextView test_results;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_results_fragment, container,
                false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewQuestionsResult);
        LinearLayoutManager llm = new LinearLayoutManager(context.getApplicationContext());
        recyclerView.setLayoutManager(llm);
        test_results = (TextView)view.findViewById(R.id.test_results);
        Test test = context.test;
        ArrayList<Question> questions = test.getQuestions();
        ArrayList<Integer> selected = new ArrayList<Integer>();
        ArrayList<Integer> correct = new ArrayList<Integer>();
        int selectCorrect = 0;
        for(int i = 0; i < questions.size(); i++) {
            selected.add(questions.get(i).getSelected());
            ArrayList<Answer> answers = questions.get(i).getAnswers();
            for (int j = 0; j < answers.size(); j++) {
                if (answers.get(j).isCorrect()) {
                    correct.add(answers.get(j).getId());
                    selectCorrect++;
                }
            }
        }
        test_results.setText("Итого: " + String.valueOf(selectCorrect/questions.size()) + "%");
        for (int i = 0; i < questions.size(); i++) {
            Question question_result = new Question("Вопрос " + String.valueOf(i + 1), selected.get(i) == correct.get(i) ? true : false);
            results.add(question_result);
        }
        adapter = new RVAdapterTestResults(context,results);
        recyclerView.setAdapter(adapter);
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
