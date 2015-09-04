package com.eisgroup.hrcrm.ui.beans;

public class Questions {
    private int id;
    private String nameQuestion;

    public Questions(int id, String nameQuestion) {
        this.id = id;
        this.nameQuestion = nameQuestion;
    }

    public int getId() {
        return id;
    }

    public String getNameQuestion() {
        return nameQuestion;
    }
}
