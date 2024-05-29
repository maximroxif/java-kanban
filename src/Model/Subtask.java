package Model;

public class Subtask extends Task {
    private int epicID;

    public Subtask(int ID, String name, String description, TaskStatus taskStatus, int epicID) {
        super(ID, name, description, taskStatus);
        this.epicID = epicID;
    }

    public Subtask(String name, String description, TaskStatus taskStatus, int epicID) {
        super(name, description, taskStatus);
        this.epicID = epicID;
    }

    public int getEpicID() {
        return epicID;
    }

    public void setEpicID(int epicID) {
        this.epicID = epicID;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "ID=" + getID() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", taskStatus=" + getTaskStatus() +
                '}';
    }
}
