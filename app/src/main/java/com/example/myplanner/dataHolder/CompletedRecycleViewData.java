package com.example.myplanner.dataHolder;

import com.example.myplanner.dataHelper.MessageHelper;

public class CompletedRecycleViewData {

    private String task_Name;
    private String task_Start_Completed;
    private String task_TimeGap_Msg;
    private int timeGap;
    private int task_db_Id;

    public CompletedRecycleViewData(String task_Name, String task_StartTime, String task_RealCompletedTime, int timeGap, int task_db_Id) {
        this.task_Name = task_Name;
        this.task_Start_Completed = task_StartTime + " - " + task_RealCompletedTime;
        this.task_TimeGap_Msg = MessageHelper.makeCompletedTaskMsg(timeGap);
        this.timeGap = timeGap;
        this.task_db_Id = task_db_Id;
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


    public int getTimeGap() {
        return timeGap;
    }

    public void setTimeGap(int timeGap) {
        this.timeGap = timeGap;
    }

    public int getTask_db_Id() {
        return task_db_Id;
    }

    public void setTask_db_Id(int task_db_Id) {
        this.task_db_Id = task_db_Id;
    }
}
