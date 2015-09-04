package com.eisgroup.hrcrm.model;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class ODSInternal extends Task {
    public ODSInternal() {
    }


    public ODSInternal(Status status, Priority priority, Complexity complexity, Resolution resolution, String summary,
                       String description, Date creationDate, Date dueDate, Date updateDate, Date closedDate, User creator,
                       User assignee) {
        super(status, priority, complexity, resolution, summary, description, creationDate, dueDate, updateDate, closedDate,
                creator, assignee);
    }

    public ODSInternal(long id, Status status, Priority priority, Complexity complexity, Resolution resolution, String summary,
                       String description, Date creationDate, Date dueDate, Date updateDate, Date closedDate, User creator,
                       User assignee) {
        super(status, priority, complexity, resolution, summary, description, creationDate, dueDate, updateDate, closedDate,
                creator, assignee);
        setId(id);
    }
}
