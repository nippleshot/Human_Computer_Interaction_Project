package com.example.myplanner;
public class Task {
    private int taskId;
    private String taskName;
    private String taskTime;//00:00
    private int imageId;//int
    private boolean done;
    private boolean repeat;
    private int timeInt;//for sorting
    public Task(String taskName,String taskTime){
        this.taskName=taskName;
        this.taskTime=taskTime;
    }
    public Task(int taskId, String taskName, String taskTime, int imageId, boolean done, boolean repeat){
        this.taskId = taskId;
        this.taskName = taskName;
        this.done = done;
        this.repeat = repeat;
        this.taskTime =taskTime;
        this.imageId = imageId;
    }
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getTaskId(){
        return taskId;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public boolean isDone() {
        return done;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public void setDone(boolean state){
        this.done=state;
    }

    public boolean getState(){return this.done;}

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTaskName(){return taskName;}

    public String getTaskTime(){return taskTime; }

    public int getImageId(){return imageId;}


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
