package com.example.kvantorium;

public class Objective {
    int id;
    private String objective;
    boolean done;

    public Objective() { }
    public Objective (String objective, boolean done) {
        this.objective = objective;
        this.done = done;
    }
    public Objective (int id, String objective, boolean done) {
        this.id = id;
        this.objective = objective;
        this.done = done;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }
    public String getObjective() {
        return objective;
    }
    public void setDone(boolean done) {
        this.done = done;
    }
    public boolean getDone() {
        return done;
    }
}
