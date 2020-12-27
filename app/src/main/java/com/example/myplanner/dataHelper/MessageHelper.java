package com.example.myplanner.dataHelper;

import com.example.myplanner.Task;

public class MessageHelper {

    /**
     *
     * @param fullDate input format : yyyy/MM/dd
     * @return "yyyy年"
     */
    public static String changeToYear(String fullDate){
        String[] date = fullDate.split("/");
        return date[0] + "年";
    }

    /**
     *
     * @param fullDate input format : yyyy/MM/dd
     * @return "MM月dd日"
     */
    public static String changeToMonth_Day(String fullDate){
        String[] date = fullDate.split("/");
        return date[1] + "月" + date[2] + "日";
    }

    /**
     *
     * @param gapMin positive or negative integer
     * @param needFrontSpace do you need one space in front of result ?
     * @return "(xx小时)xx分钟"
     */
    public static String changeToHour_Min(int gapMin, boolean needFrontSpace){
        if(gapMin<0){
            gapMin = -1 * gapMin;
        }

        if(gapMin>=60){
            int hour = gapMin/60;
            int min = gapMin - (60*hour);

            if(min==0){
                return needFrontSpace? " " + hour + "小时" : hour + "小时";
            }else{
                return needFrontSpace? " " + hour + "小时" + min + "分钟" : hour + "小时" + min + "分钟";
            }

        }else{
            return needFrontSpace? " " + gapMin + "分钟" : gapMin + "分钟";
        }
    }


    /**
     *
     * @param task
     * @return "迟到(xx小时)xx分钟" or "提前(xx小时)xx分钟"
     */
    public static String makeCompletedTaskMsg(Task task){
        if(task.isCompleted()){
            int gapMin = TaskHelper.countGapInMin(task);

            // return < 0 min : postponed finishing task
            // return > 0 min : task finished earlier
            if(gapMin < 0){

            }else{

            }

        }else{
            return "MessageHelper.makeCompletedTaskMsg() Error : this task is uncompleted task";
        }
        return null;
    }

    /**
     *
     * @param task
     * @return "开始前(xx小时)xx分钟"
     */
    public static String makeUncompletedTaskMsg(Task task){


        return null;
    }


}
