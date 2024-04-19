class Subtask extends Task {
    private Epic epic;

    public Subtask(String name, String description, TaskStatus taskStatus, Epic epic) {
        super(name, description, taskStatus);
        this.epic = epic;
    }

    public Epic getEpic() {
        return epic;
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
