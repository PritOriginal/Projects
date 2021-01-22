package com.example.kvantorium;

import java.util.ArrayList;

public class Question {
    private int id;
    private String question;
    private ArrayList<Answer> answers;

    public Question(int id, String question, ArrayList<Answer> answers) {
        this.id = id;
        this.question = question;
        this.answers = answers;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }
    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }
}
