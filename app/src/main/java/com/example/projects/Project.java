package com.example.projects;

public class Project {
    private int id;
    private String nameProject;
    private String descriptionProject;
    private boolean completed;
    public Project(){}
    public Project (int id, String nameProject, String descriptionProject, boolean completed) {
        this.id = id;
        this.nameProject = nameProject;
        this.descriptionProject = descriptionProject;
        this.completed = completed;
    }

    public Project(int anInt, String string, String string1) {
    }

    public void setName(String n){
       nameProject = n;
    }
    public String getName(){
        return nameProject;
    }
    public void setDescription(String d){
        descriptionProject = d;
    }
    public String  getDescription(){
        return descriptionProject;
    }
    public void setId(int i) { id = i; }
    public int getId() { return id; }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    public boolean isCompleted() {
        return completed;
    }
}
