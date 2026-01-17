package model;

public class TimedTask extends Task {
    private int estimatedMinutes;

    // Constructor (inherits from Task)
    public TimedTask(String taskId, String taskName, String taskDescription, Status status, int estimatedMinutes) {
        super(taskId, taskName, taskDescription, status);
        this.estimatedMinutes = estimatedMinutes;
    }

    // Encapsulated getter and setter for estimatedMinutes
    public int getEstimatedMinutes() {
        return estimatedMinutes;
    }

    public void setEstimatedMinutes(int estimatedMinutes) {
        this.estimatedMinutes = estimatedMinutes;
    }
}
