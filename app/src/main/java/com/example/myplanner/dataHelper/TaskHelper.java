package com.example.myplanner.dataHelper;

import com.example.myplanner.Task;
import com.example.myplanner.dataHolder.CompletedRecycleViewData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TaskHelper {


    public static boolean isTaskCompletedInTime(Task task){
        if(task.isCompleted()){
            if(countGapInMin(task) > 0){
                return true;
            }
        }
        return false;
    }


    /**
     *
     * @param completeDateTime      input format must be yyyy/MM/ddHH:mm
     * @param realCompleteDateTime  input format must be yyyy/MM/ddHH:mm
     * @return completeDateTime - realCompleteDateTime (单位 ： 分钟)
     *  return < 0 min : postponed finishing task
     *  return > 0 min : task finished earlier
     */
    public static int subtractDateTime(String completeDateTime, String realCompleteDateTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/ddHH:mm");

        Date plannedDateTime;
        Date realDateTime;
        try {
            plannedDateTime = dateFormat.parse(completeDateTime);
            realDateTime = dateFormat.parse(realCompleteDateTime);

            long plannedTime = plannedDateTime.getTime();
            long realTime = realDateTime.getTime();

            return (int)((plannedTime - realTime) / 60000);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }



    /**
     * calculate gap between task.CompleteDate&Time - task.taskRealCompleteDate&Time
     * @param task
     * @return
     *  return min : if this task is completed task
     *  return Integer.MAX_VALUE  : if this task is not completed task
     *
     */
    public static int countGapInMin(Task task){
        if(task.isCompleted()){
            return subtractDateTime(task.getTaskCompleteDate()+task.getTaskCompleteTime(),
                    task.getTaskRealCompleteDate()+task.getTaskRealCompleteTime() );
        }else{
            return 0;
        }
    }

    /**
     * using when counting total efficiency (CompletedTaskAdaptor, StraggeredCardAdapter)
     * @param tasks
     * @return
     *  return min : if all task are completed task
     *  return Integer.MAX_VALUE  : if one of tasks is not completed task
     */
    public static int countSumOfGap(ArrayList<Task> tasks){
        int min_Sum = 0;
        int taskGap;
        for(int i=0; i<tasks.size(); i++){
            taskGap = countGapInMin(tasks.get(i));
            min_Sum = min_Sum + taskGap;
        }

        return min_Sum;
    }

    public static int countEfficiency(ArrayList<CompletedRecycleViewData> tasks){
        int min_Sum = 0;
        int taskGap;
        for(int i=0; i<tasks.size(); i++){
            taskGap = tasks.get(i).getTimeGap();
            min_Sum = min_Sum + taskGap;
        }

        return min_Sum;

    }

    public static int countCompletedTask(ArrayList<Task> tasks){
        int count = 0;
        for(int i=0; i<tasks.size(); i++){
            if(tasks.get(i).isCompleted()){
                count = count + 1;
            }
        }

        return count;
    }

    public static int countCompletedInTimeTask(ArrayList<Task> tasks){
        int count = 0;
        for(int i=0; i<tasks.size(); i++){
            if(tasks.get(i).isCompleted() && tasks.get(i).isCompletedInTime()){
                count = count + 1;
            }
        }

        return count;
    }



    public static int countUncompletedTask(ArrayList<Task> tasks){
        int count = 0;
        for(int i=0; i<tasks.size(); i++){
            if(!tasks.get(i).isCompleted()){
                count = count + 1;
            }
        }

        return count;
    }


    public static ArrayList<Task> getCompletedTasks(ArrayList<Task> tasks){
        ArrayList<Task> result = new ArrayList<>();
        for(int i=0; i<tasks.size(); i++){
            if(tasks.get(i).isCompleted()){
                result.add(tasks.get(i));
            }
        }

        return result;
    }

    public static ArrayList<Task> getUncompletedTasks(ArrayList<Task> tasks){
        ArrayList<Task> result = new ArrayList<>();
        for(int i=0; i<tasks.size(); i++){
            if( !(tasks.get(i).isCompleted()) && !(tasks.get(i).isCompletedInTime())){
                result.add(tasks.get(i));
            }
        }

        return result;
    }





}
