package com.example.myplanner.dataHolder;

public class CompletedRecycleViewData {

    private String task_Name;
    private String task_Start_Completed;
    private String task_TimeGap_Msg;

    public CompletedRecycleViewData(String task_Name, String task_StartTime, String task_RealCompletedTime, String task_TimeGap_Msg) {
        this.task_Name = task_Name;
        this.task_Start_Completed = task_StartTime + " - " + task_RealCompletedTime;
        this.task_TimeGap_Msg = task_TimeGap_Msg;
    }


    public String getTask_Name() {
        return task_Name;
    }

    public void setTask_Name(String task_Name) {
        this.task_Name = task_Name;
    }

    public String getTask_Start_Completed() {
        return task_Start_Completed;
    }

    public void setTask_Start_Completed(String task_StartTime, String task_RealCompletedTime) {
        this.task_Start_Completed = task_StartTime + " - " + task_RealCompletedTime;
    }

    public String getTask_TimeGap_Msg() {
        return task_TimeGap_Msg;
    }

    public void setTask_TimeGap_Msg(String task_TimeGap_Msg) {
        this.task_TimeGap_Msg = task_TimeGap_Msg;
    }
}
