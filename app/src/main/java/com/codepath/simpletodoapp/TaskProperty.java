package com.codepath.simpletodoapp;

public class TaskProperty {
    public String taskName;
    public String dueDate;
    public String priority;
    public String id;

    public TaskProperty(String taskName, String priority,String dueDate, String id) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.priority= priority;
        this.id= id;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getPriority() {
        return priority;
    }
}
