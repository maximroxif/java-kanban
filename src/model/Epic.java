package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;


public class Epic extends Task {
    private ArrayList<Subtask> subtasks = new ArrayList<>();
    protected LocalDateTime endTime = null;

    public Epic(String name, String description) {
        super(name, description);
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void addSubTask(Subtask subtask) {
        subtasks.add(subtask);
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

    public void updateTime() {
        if (subtasks.isEmpty()) {
            setStartTime(null);
            setEndTime(null);
            setDuration(null);
        } else {
            Set<Task> prioritizedSubasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));
            prioritizedSubasks.addAll(subtasks);
            setStartTime(prioritizedSubasks.stream().findFirst().get().getStartTime());
            Duration duration = Duration.ofMinutes(0);
            for (Task tasks : prioritizedSubasks) {
                duration = duration.plus(tasks.getDuration());
                setDuration(duration);
                if (endTime.isBefore(tasks.getEndTime())) {
                    endTime = tasks.getEndTime();
                }
            }
        }
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
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
