package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Subtask extends Task {
    private int epicid;

    public Subtask(String name, String description, LocalDateTime startTime, Duration duration, int epicid) {
        super(name, description, startTime, duration);
        this.epicid = epicid;
    }

    public Subtask(Subtask subtask) {
        super(subtask.name, subtask.description, subtask.startTime, subtask.duration);
        this.epicid = subtask.epicid;
        this.id = subtask.id;
        this.taskStatus = subtask.taskStatus;
    }

//    public Subtask(String name, String description, TaskStatus taskStatus, int epicid, Duration duration, LocalDateTime startTime) {
//        super(name, description, taskStatus, duration, startTime);
//        this.epicid = epicid;
//    }
//
//    public Subtask(int id, String name, String description, TaskStatus taskStatus, int epicid) {
//        super(id, name, description, taskStatus);
//        this.epicid = epicid;
//    }
//
//    public Subtask(String name, String description, TaskStatus taskStatus, int epicid) {
//        super(name, description, taskStatus);
//        this.epicid = epicid;
//    }

    public int getEpicid() {
        return epicid;
    }

    public void setEpicid(int epicid) {
        this.epicid = epicid;
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.SUBTASK;
    }

    public int getEpicId() {
        return epicid;
    }


    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", taskStatus=" + taskStatus +
                ", startTime=" + startTime.format(DateTimeFormatter.ofPattern("yy.MM.dd HH:mm")) +
                ", duration=" + duration.toMinutes() +
                '}';
    }
}
