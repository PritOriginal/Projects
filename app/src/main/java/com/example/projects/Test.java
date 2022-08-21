package com.example.projects;

import java.util.ArrayList;

public class Test {
    private int id;
    private String name;
    private ArrayList<Question> questions;
    private boolean completed;
    private int progress;

    public Test() {

    }
    public Test(int id, String name, boolean completed, int progress) {
        this.id = id;
        this.name = name;
        this.completed = completed;
        this.progress = progress;
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

    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(boolean complete) {
        this.completed = complete;
    }

    public int getProgress() {
        return progress;
    }
    public void setProgress(int progress) {
        this.progress = progress;
    }
}
