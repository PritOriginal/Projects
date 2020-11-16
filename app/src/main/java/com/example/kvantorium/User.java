package com.example.kvantorium;

public class User {
    private int id;
    private String firstName;
    private String secondName;
    private int raiting;
    private String role;
    private int group;
    private String vk;

    public User(){
    }

    public User(int id, String firstName, String secondName, String role) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.role = role;
    }

    public User(int id, String firstName, String secondName, String role, int group) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.role = role;
        this.group = group;
    }

    public User(int id, String firstName, String secondName, String role, String vk) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.role = role;
        this.vk = vk;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getRaiting() {
        return raiting;
    }
    public void setRaiting(int raiting) {
        this.raiting = raiting;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
    }

    public int getGroup() {
        return group;
    }
    public void setGroup(int group) {
        this.group = group;
    }

    public void setVk(String vk) {
        this.vk = vk;
    }
    public String getVk() {
        return vk;
    }
}
