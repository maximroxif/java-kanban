import java.util.ArrayList;

class Epic extends Task {
    private ArrayList<Subtask> subtasks = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description, TaskStatus.NEW);
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void addSubTask(Subtask subtask) {
        this.subtasks.add(subtask);
    }

    public void updateStatus() {
        if (subtasks.isEmpty()) {
            setTaskStatus(TaskStatus.NEW);
            return;
        }
        boolean allNew = true;
        boolean allDone = true;
        for (Subtask subtask : this.subtasks) {
            if (subtask.getTaskStatus() != TaskStatus.NEW) {
                allNew = false;
            }
            if (subtask.getTaskStatus() != TaskStatus.DONE) {
                allDone = false;
            }
            if (!allNew && !allDone) {
                setTaskStatus(TaskStatus.IN_PROGRESS);
            }
        }
        if (allNew) {
            setTaskStatus(TaskStatus.NEW);
        } else if (allDone) {
            setTaskStatus(TaskStatus.DONE);
        } else {
            setTaskStatus(TaskStatus.IN_PROGRESS);
        }
    }

    @Override
    public String toString() {
        return "Epic{" +
                "ID=" + getID() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", taskStatus=" + getTaskStatus() +
                '}';
    }
}
