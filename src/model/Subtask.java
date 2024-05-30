package model;

public class Subtask extends Task {
    private int epicid;

    public Subtask(int id, String name, String description, TaskStatus taskStatus, int epicid) {
        super(id, name, description, taskStatus);
        this.epicid = epicid;
    }

    public Subtask(String name, String description, TaskStatus taskStatus, int epicid) {
        super(name, description, taskStatus);
        this.epicid = epicid;
    }

    public int getEpicid() {
        return epicid;
    }

    public void setEpicid(int epicid) {
        this.epicid = epicid;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + getid() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", taskStatus=" + getTaskStatus() +
                '}';
    }
}
