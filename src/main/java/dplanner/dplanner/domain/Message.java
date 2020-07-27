package dplanner.dplanner.domain;

import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Message {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String header;
    private String comments;
    private ZonedDateTime created;
    private ZonedDateTime completionTime;
    private ZonedDateTime plannedTime;
    private ZonedDateTime prevPlannedTime;
    private Boolean completed;

    public Message() {
    }

    public Message(String header, String comments, ZonedDateTime plannedTime, ZonedDateTime completionTime) {
        this.header = header;
        this.comments = comments;
        this.created = ZonedDateTime.now();
        this.completionTime = completionTime;
        this.plannedTime = plannedTime;
        this.prevPlannedTime = plannedTime;
        this.completed = false;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + (id == null ? 0 : id.hashCode());
      result = prime * result + (header == null ? 0 : header.hashCode());
      result = prime * result + (comments == null ? 0 : comments.hashCode());
      result = prime * result + (created == null ? 0 : created.hashCode());
      result = prime * result + (completionTime == null ? 0 : completionTime.hashCode());
      result = prime * result + (plannedTime == null ? 0 : plannedTime.hashCode());
      result = prime * result + (prevPlannedTime == null ? 0 : prevPlannedTime.hashCode());
      result = prime * result + (completed == null ? 0 : completed.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Message other = (Message) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;

        if (header == null) {
            if (other.header != null)
                return false;
        } else if (!header.equals(other.header))
                return false;

        if (comments == null) {
            if (other.comments != null)
                return false;
        } else if (!comments.equals(other.comments))
                return false;

        if (created == null) {
            if (other.created != null)
                return false;
        } else if (!created.equals(other.created))
            return false;

        if (completionTime == null) {
            if (other.completionTime != null)
                return false;
        } else if (!completionTime.equals(other.completionTime))
            return false;

        if (plannedTime == null) {
            if (other.plannedTime != null)
                return false;
        } else if (!plannedTime.equals(other.plannedTime))
            return false;

        if (prevPlannedTime == null) {
            if (other.prevPlannedTime != null)
                return false;
        } else if (!prevPlannedTime.equals(other.prevPlannedTime))
            return false;

        if (completed == null) {
            if (other.completed != null)
                return false;
        } else if (!completed.equals(other.completed))
            return false;
        return true;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
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

    public ZonedDateTime getCreated() {
        return this.created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getCompletionTime() {
        return this.completionTime;
    }

    public void setCompletionTime(ZonedDateTime completionTime) {
        this.completionTime = completionTime;
    }

    public ZonedDateTime getPlannedTime() {
        return this.plannedTime;
    }

    public void setPlannedTime(ZonedDateTime plannedTime) {
        this.plannedTime = plannedTime;
    }

    public ZonedDateTime getPrevPlannedTime() {
        return this.prevPlannedTime;
    }

    public void setPrevPlannedTime(ZonedDateTime prevPlannedTime) {
        this.prevPlannedTime = prevPlannedTime;
    }

    public Boolean isCompleted() {
        return this.completed;
    }

    public Boolean getCompleted() {
        return this.completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}