package dplanner.dplanner.dto;

public class Event {
    
    private Long id;
    private String name;
    private String details;
    private boolean completed;
    private String start;
    private String end;

    public Event() {
    }

    public Event(Long id, String name, String details, boolean completed, String start, String end) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.completed = completed;
        this.start = start;
        this.end = end;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return this.details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public boolean isCompleted() {
        return this.completed;
    }

    public boolean getCompleted() {
        return this.completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getStart() {
        return this.start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return this.end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}