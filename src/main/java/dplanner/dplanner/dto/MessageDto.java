package dplanner.dplanner.dto;

public class MessageDto {

    private long id;
    private String header;
    private String comments;
    private String created;
    private String completionTime;
    private String plannedTime;
    private boolean completed;

    public MessageDto() {
    }

    public MessageDto(long id, String header, String comments, boolean completed) {
        if (comments == null || comments.isEmpty())
            comments = "-- -- --";
        this.id = id;
        this.header = header;
        this.comments = comments;
        this.completed = completed;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHeader() {
        return this.header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCreated() {
        return this.created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCompletionTime() {
        return this.completionTime;
    }

    public void setCompletionTime(String completionTime) {
        this.completionTime = completionTime;
    }

    public String getPlannedTime() {
        return this.plannedTime;
    }

    public void setPlannedTime(String plannedTime) {
        this.plannedTime = plannedTime;
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
}