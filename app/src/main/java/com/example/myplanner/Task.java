package com.example.myplanner;
public class Task {
    private int taskId;
    private String taskName;
    private String 	taskStartDate;
    private String 	taskStartTime;
    private String 	taskCompleteDate;
    private String	taskCompleteTime;
    private String	taskRealCompleteDate;
    private String	taskRealCompleteTime;
    private String	taskPlace;
    private String	taskMemo;
    private boolean isCompleted;		//该任务是不是完成？
    private boolean	isCompletedInTime; 	//该任务是不是在制定的时间内完成？


    public Task(String taskName, String taskStartDate, String taskStartTime, String taskCompleteDate, String taskCompleteTime, String taskPlace, String taskMemo) {
        this.taskName = taskName;
        this.taskStartDate = taskStartDate;
        this.taskStartTime = taskStartTime;
        this.taskCompleteDate = taskCompleteDate;
        this.taskCompleteTime = taskCompleteTime;
        this.taskPlace = taskPlace;
        this.taskMemo = taskMemo;


        this.taskRealCompleteDate = null;
        this.taskRealCompleteTime = null;
        this.isCompleted = false;
        this.isCompletedInTime = false;
    }

    public Task(int taskId) {
        this.taskId = taskId;
    }

    public Task(int taskId, String taskName, String taskStartDate, String taskStartTime, String taskCompleteDate, String taskCompleteTime, String taskRealCompleteDate, String taskRealCompleteTime, String taskPlace, String taskMemo, boolean isCompleted, boolean isCompletedInTime) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskStartDate = taskStartDate;
        this.taskStartTime = taskStartTime;
        this.taskCompleteDate = taskCompleteDate;
        this.taskCompleteTime = taskCompleteTime;
        this.taskRealCompleteDate = taskRealCompleteDate;
        this.taskRealCompleteTime = taskRealCompleteTime;
        this.taskPlace = taskPlace;
        this.taskMemo = taskMemo;
        this.isCompleted = isCompleted;
        this.isCompletedInTime = isCompletedInTime;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskStartDate() {
        return taskStartDate;
    }

    public void setTaskStartDate(String taskStartDate) {
        this.taskStartDate = taskStartDate;
    }

    public String getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskStartTime(String taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public String getTaskCompleteDate() {
        return taskCompleteDate;
    }

    public void setTaskCompleteDate(String taskCompleteDate) {
        this.taskCompleteDate = taskCompleteDate;
    }

    public String getTaskCompleteTime() {
        return taskCompleteTime;
    }

    public void setTaskCompleteTime(String taskCompleteTime) {
        this.taskCompleteTime = taskCompleteTime;
    }

    public String getTaskRealCompleteDate() {
        return taskRealCompleteDate;
    }

    public void setTaskRealCompleteDate(String taskRealCompleteDate) {
        this.taskRealCompleteDate = taskRealCompleteDate;
    }

    public String getTaskRealCompleteTime() {
        return taskRealCompleteTime;
    }

    public void setTaskRealCompleteTime(String taskRealCompleteTime) {
        this.taskRealCompleteTime = taskRealCompleteTime;
    }

    public String getTaskPlace() {
        return taskPlace;
    }

    public void setTaskPlace(String taskPlace) {
        this.taskPlace = taskPlace;
    }

    public String getTaskMemo() {
        return taskMemo;
    }

    public void setTaskMemo(String taskMemo) {
        this.taskMemo = taskMemo;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public boolean isCompletedInTime() {
        return isCompletedInTime;
    }

    public void setCompletedInTime(boolean completedInTime) {
        isCompletedInTime = completedInTime;
    }
}

