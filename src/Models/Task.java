package Models;

public class Task {
    private int id;
    private String description;
    private Boolean isDone;
    private Boolean isDelete;

    public Task(int id, String description) {
        this.id = id;
        this.description = description;
        this.isDone = false;
        this.isDelete = false;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public Task(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}

