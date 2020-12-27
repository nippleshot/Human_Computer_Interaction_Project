package com.example.myplanner.dataHelper;

import com.example.myplanner.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TaskHelper {

    /**
     *
     * @param completeDateTime      input format must be yyyy/MM/ddHH:mm
     * @param realCompleteDateTime  input format must be yyyy/MM/ddHH:mm
     * @return minutes
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

            System.out.println(plannedTime);
            System.out.println(realTime);

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
     *  return -1  : if this task is not completed task
     *
     */
    public static int countGapInMin(Task task){
        if(task.isCompleted()){
            return subtractDateTime(task.getTaskCompleteDate()+task.getTaskCompleteTime(),
                    task.getTaskRealCompleteDate()+task.getTaskRealCompleteTime() );
        }else{
            return -1;
        }
    }


    public static int countSumOfGap(ArrayList<Task> tasks){


        return 0;
    }


}
