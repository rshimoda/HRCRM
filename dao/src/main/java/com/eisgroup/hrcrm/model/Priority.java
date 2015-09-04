package com.eisgroup.hrcrm.model;

public enum Priority {
    LOW("Low", 1),
    MEDIUM("Medium", 2),
    HIGH("High", 3),
    CRITICAL("Critical", 4);

    private final String name;
    private final int sortIndex;

    Priority(String name, int sortIndex) {
        this.name = name;
        this.sortIndex = sortIndex;
    }

    public String getName() {
        return name;
    }

    public int getSortIndex() {
        return sortIndex;
    }


}
