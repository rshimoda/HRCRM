package com.eisgroup.hrcrm.model;

public enum Source {
    INTERNAL("Internal"),
    EXTERNAL("External");

    private final String name;

    Source(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}