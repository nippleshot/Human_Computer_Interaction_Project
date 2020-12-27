package com.example.myplanner;
public class Task {
    private int taskId;
    private String taskName;

    public Task(String taskName){
        this.taskName = taskName;
    }

    public Task(int id, String taskName){
        this.taskId = id;
        this.taskName = taskName;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getTaskId(){
        return taskId;
    }

    public String getTaskName(){return taskName;}

    public void setTaskName(String taskName){
        this.taskName = new String(taskName);
    }

}
//class Task {
//    private String taskName;
//    private String taskTime;
//    private int imageId;
//    private boolean done;
//    private int timeInt;//for sorting
//    public Task(String taskName,String taskTime){
//        this.taskName=taskName;
//        this.taskTime=taskTime;
//    }
//public void setDone(boolean state){
//        this.done=state;
//}
//public boolean getState(){return this.done;}
//    public void setImageId(int imageId) {
//        this.imageId = imageId;
//    }
//    public String getTaskName(){return taskName;}
//    public String getTaskTime(){return taskTime; }
//    public int getImageId(){return imageId;}
//
//}
