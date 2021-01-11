package com.example.kvantorium;

import java.util.ArrayList;

public class Test {
    private int id;
    private String name;
    private ArrayList<Question> questions;
    private boolean complete;

    public Test(int id, String name, boolean complete) {
        this.id = id;
        this.name = name;
    }
    public Test(int id, String name, ArrayList<Question> questions) {
        this.id = id;
        this.name = name;
        this.questions = questions;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public boolean isComplete() {
        return complete;
    }
    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
