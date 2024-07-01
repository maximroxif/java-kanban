package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class Epic extends Task {
    private final ArrayList<Subtask> subtasks = new ArrayList<>();
    protected LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, description, null, Duration.ofMinutes(0));
    }

    public Epic(Epic epic) {
        super(epic.name, epic.description, null, Duration.ofMinutes(0));
        this.endTime = null;
        this.id = epic.id;
    }

    //    public Epic(String name, String description ) {
//        super(name, description, TaskStatus.NEW, Duration.ofMinutes(0), null);
//    }

//    public Epic(String name, String description) {
//        super(name, description, TaskStatus.NEW);
//    }

//    public Epic(int id, String name, String description, TaskStatus taskStatus) {
//        super(id, name, description, taskStatus);
//    }

//    public Epic(int id, String name, String description) {
//        super(id, name, description, TaskStatus.NEW);
//    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void addSubTask(Subtask subtask) {
        this.subtasks.add(subtask);
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.EPIC;
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

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", taskStatus=" + taskStatus +
                ", startTime=" + startTime.format(DateTimeFormatter.ofPattern("yy.MM.dd HH:mm")) +
                ", duration=" + duration.toMinutes() +
                '}';
    }
}
