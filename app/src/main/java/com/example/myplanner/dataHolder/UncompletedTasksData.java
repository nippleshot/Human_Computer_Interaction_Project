package com.example.myplanner.dataHolder;

public class UncompletedTasksData {
    private boolean expandable;

    private int task_db_index;
    private String task_Name;
    private String task_TimeMsg;
    private String task_Place;
    private String task_Memo;

    public UncompletedTasksData(int task_db_index, String task_Name, String task_TimeMsg, String task_Place, String task_Memo) {
        this.expandable = false;
        this.task_db_index = task_db_index;
        this.task_Name = task_Name;
        this.task_TimeMsg = task_TimeMsg;
        this.task_Place = task_Place;
        this.task_Memo = task_Memo;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public int getTask_db_index() {
        return task_db_index;
    }

    public void setTask_db_index(int task_db_index) {
        this.task_db_index = task_db_index;
    }

    public String getTask_Name() {
        return task_Name;
    }

    public void setTask_Name(String task_Name) {
        this.task_Name = task_Name;
    }

    public String getTask_TimeMsg() {
        return task_TimeMsg;
    }

    public void setTask_TimeMsg(String task_TimeMsg) {
        this.task_TimeMsg = task_TimeMsg;
    }

    public String getTask_Place() {
        return task_Place;
    }

    public void setTask_Place(String task_Place) {
        this.task_Place = task_Place;
    }

    public String getTask_Memo() {
        return task_Memo;
    }

    public void setTask_Memo(String task_Memo) {
        this.task_Memo = task_Memo;
    }
}
