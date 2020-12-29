package com.example.myplanner.dataHelper;

import com.example.myplanner.Task;
import com.example.myplanner.dataHolder.CardViewData;
import com.example.myplanner.dataHolder.CompletedRecycleViewData;
import com.example.myplanner.dataHolder.CompletedTasksData;
import com.example.myplanner.dataHolder.RecycleViewData;
import com.example.myplanner.dataHolder.UncompletedTasksData;

import java.util.ArrayList;

public class Converter {

    public static CardViewData toCardViewData(String day_Date, ArrayList<Task> day_Date_Tasks){

        int day_TotalTaskNum = day_Date_Tasks.size();
        int day_EfficiencyMin = TaskHelper.countSumOfGap(day_Date_Tasks);
        int day_UncompletedTaskNum = TaskHelper.countUncompletedTask(day_Date_Tasks);

        return new CardViewData(day_Date, day_TotalTaskNum, day_EfficiencyMin, day_UncompletedTaskNum);
    }

    public static CompletedRecycleViewData toCompletedRecycleViewData(Task task){
        return new CompletedRecycleViewData(task.getTaskName(), task.getTaskStartTime(), task.getTaskRealCompleteTime(), TaskHelper.countGapInMin(task));
    }

    public static ArrayList<CompletedRecycleViewData> toCompletedRecycleViewDataList(ArrayList<Task> day_Date_CompletedTasks){
        ArrayList<CompletedRecycleViewData> result = new ArrayList<>();

        Task task;
        for(int i=0; i<day_Date_CompletedTasks.size(); i++){
            task = day_Date_CompletedTasks.get(i);
            result.add( new CompletedRecycleViewData(task.getTaskName(), task.getTaskStartTime(), task.getTaskRealCompleteTime(), TaskHelper.countGapInMin(task)) );
        }

        return result;
    }

    public static CompletedTasksData toCompletedTaskData(ArrayList<Task> day_Date_AllTasks){

        ArrayList<Task> day_Date_CompletedTasks = TaskHelper.getCompletedTasks( day_Date_AllTasks );

        ArrayList<CompletedRecycleViewData> completedRecycleViewDataList = toCompletedRecycleViewDataList(day_Date_CompletedTasks);

        return new CompletedTasksData( day_Date_AllTasks.get(0).getTaskStartDate(),
                                        day_Date_AllTasks.size(),
                                        TaskHelper.countCompletedTask( day_Date_AllTasks ),
                                        TaskHelper.countCompletedInTimeTask( day_Date_AllTasks ),
                                        TaskHelper.countUncompletedTask( day_Date_AllTasks ),
                                        completedRecycleViewDataList
                                        );
    }

    public static UncompletedTasksData toUncompletedTasksData(Task task){

        return new UncompletedTasksData( task.getTaskId(),
                                        task.getTaskName(),
                                        MessageHelper.makeUncompletedTaskMsg(task),
                                        task.getTaskPlace(),
                                        task.getTaskMemo(),
                                        task.getTaskStartTime() + " - " + task.getTaskCompleteTime()
                                        );
    };


    public static ArrayList<RecycleViewData> toRecycleViewDataList(ArrayList<Task> day_Date_AllTasks){
        ArrayList<RecycleViewData> result = new ArrayList<>();

        result.add( new RecycleViewData( toCompletedTaskData(day_Date_AllTasks), null, 1) );

        ArrayList<Task> day_Date_UncompletedTasks = TaskHelper.getUncompletedTasks(day_Date_AllTasks);
        for(int i=0; i<day_Date_UncompletedTasks.size(); i++){
            result.add( new RecycleViewData( null, toUncompletedTasksData(day_Date_UncompletedTasks.get(i)), 2) );
        }


        return result;
    }


}
