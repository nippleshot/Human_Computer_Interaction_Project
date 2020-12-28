package com.example.myplanner.dataHolder;

import com.example.myplanner.dataHelper.TaskHelper;

import java.util.ArrayList;
import java.util.List;

public class CompletedTasksData {

    private boolean expandable;

    private String task_Date;
    private int task_Total,  task_CompletedNum, task_CompletedInTimeNum, task_UncompletedNum, task_TotalEfficiency;
    private ArrayList<CompletedRecycleViewData> completedRecycleViewDataList;

    public CompletedTasksData(String task_Date, int task_Total, int task_CompletedNum, int task_CompletedInTimeNum, int task_UncompletedNum, ArrayList<CompletedRecycleViewData> completedRecycleViewDataList) {
        this.expandable = false;
        this.task_Date = task_Date;
        this.task_Total = task_Total;
        this.task_CompletedNum = task_CompletedNum;
        this.task_CompletedInTimeNum = task_CompletedInTimeNum;
        this.task_UncompletedNum = task_UncompletedNum;
        this.completedRecycleViewDataList = completedRecycleViewDataList;
        this.task_TotalEfficiency = TaskHelper.countEfficiency(completedRecycleViewDataList);
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public String getTask_Date() {
        return task_Date;
    }

    public void setTask_Date(String task_Date) {
        this.task_Date = task_Date;
    }

    public int getTask_Total() {
        return task_Total;
    }

    public void setTask_Total(int task_Total) {
        this.task_Total = task_Total;
    }

    public int getTask_CompletedNum() {
        return task_CompletedNum;
    }

    public void setTask_CompletedNum(int task_CompletedNum) {
        this.task_CompletedNum = task_CompletedNum;
    }

    public int getTask_CompletedInTimeNum() {
        return task_CompletedInTimeNum;
    }

    public void setTask_CompletedInTimeNum(int task_CompletedInTimeNum) {
        this.task_CompletedInTimeNum = task_CompletedInTimeNum;
    }

    public int getTask_UncompletedNum() {
        return task_UncompletedNum;
    }

    public void setTask_UncompletedNum(int task_UncompletedNum) {
        this.task_UncompletedNum = task_UncompletedNum;
    }

    public ArrayList<CompletedRecycleViewData> getCompletedRecycleViewDataList() {
        return completedRecycleViewDataList;
    }

    public void setCompletedRecycleViewDataList(ArrayList<CompletedRecycleViewData> completedRecycleViewDataList) {
        this.completedRecycleViewDataList = completedRecycleViewDataList;
    }

    public int getTask_TotalEfficiency() {
        return task_TotalEfficiency;
    }

    public void setTask_TotalEfficiency(int task_TotalEfficiency) {
        this.task_TotalEfficiency = task_TotalEfficiency;
    }
}
