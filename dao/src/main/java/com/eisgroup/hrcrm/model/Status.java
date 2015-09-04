package com.eisgroup.hrcrm.model;

public enum Status {
    OPEN("Open"),
    CLOSED("Closed"),
    IN_PROGRESS("In Progress"),
    RESOLVED("Resolved"),
    REOPENED("Reopened");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
