package com.example.myplanner.dataHolder;

/**
 * int viewType :
     1 = HeadViewHolder : CompletedTasksData;
     2 = TaskViewHolder : UncompletedTasksData;
 */
public class RecycleViewData {
    private CompletedTasksData analysisViewData;
    private UncompletedTasksData checkListViewData;
    private int viewType;

    public RecycleViewData(CompletedTasksData analysisViewData, UncompletedTasksData checkListViewData, int viewType) {
        this.analysisViewData = analysisViewData;
        this.checkListViewData = checkListViewData;
        this.viewType = viewType;
    }

    public CompletedTasksData getAnalysisViewData() {
        return analysisViewData;
    }

    public void setAnalysisViewData(CompletedTasksData analysisViewData) {
        this.analysisViewData = analysisViewData;
    }

    public UncompletedTasksData getCheckListViewData() {
        return checkListViewData;
    }

    public void setCheckListViewData(UncompletedTasksData checkListViewData) {
        this.checkListViewData = checkListViewData;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
