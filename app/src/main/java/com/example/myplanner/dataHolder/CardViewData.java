package com.example.myplanner.dataHolder;

public class CardViewData {
    private String day_Date;
    private int day_TotalTaskNum;
    private int day_EfficiencyMin;
    private int day_UncompletedTaskNum;


    public CardViewData(String day_Date, int day_TotalTaskNum, int day_EfficiencyMin, int day_UncompletedTaskNum) {
        this.day_Date = day_Date;
        this.day_TotalTaskNum = day_TotalTaskNum;
        this.day_EfficiencyMin = day_EfficiencyMin;
        this.day_UncompletedTaskNum = day_UncompletedTaskNum;
    }


    public String getDay_Date() {
        return day_Date;
    }

    public void setDay_Date(String day_Date) {
        this.day_Date = day_Date;
    }

    public int getDay_TotalTaskNum() {
        return day_TotalTaskNum;
    }

    public void setDay_TotalTaskNum(int day_TotalTaskNum) {
        this.day_TotalTaskNum = day_TotalTaskNum;
    }

    public int getDay_EfficiencyMin() {
        return day_EfficiencyMin;
    }

    public void setDay_EfficiencyMin(int day_EfficiencyMin) {
        this.day_EfficiencyMin = day_EfficiencyMin;
    }

    public int getDay_UncompletedTaskNum() {
        return day_UncompletedTaskNum;
    }

    public void setDay_UncompletedTaskNum(int day_UncompletedTaskNum) {
        this.day_UncompletedTaskNum = day_UncompletedTaskNum;
    }
}
