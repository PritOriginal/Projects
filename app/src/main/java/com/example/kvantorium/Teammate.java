package com.example.kvantorium;

public class Teammate {
    private int id;
    private String firstName;
    private String secondName;
    private String role;

    public Teammate(int id, String firstName, String secondName, String role) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.role = role;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
    public String getSecondName() {
        return secondName;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getRole() {
        return role;
    }
}
