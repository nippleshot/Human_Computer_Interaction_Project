package com.example.myplanner.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myplanner.Task;

import java.util.ArrayList;
/**
 * call getDataBase(this) to get the single database
 * call createTask(Task task) to add task in the database and return task id
 * call retrieve() to get an ArrayList with all tasks
 * call deleteTask(Task task) to delete the task
 * call updateTask(Task task) to update the task
 * call getTaskById(int id) to get task by id
 * call getTasksByDate(String taskStartDate) to get task ArrayList by date
 * call getAllDate() to get an ArrayList with all date
**/

/**
 * Date格式是yyyy/MM/dd
 * Time格式是HH:mm
 */
public class DataBase {
    private final int VERSION = 0;

    private DataBaseHelper dbHelper; //helper

    private SQLiteDatabase taskTables; //database

    private Context context; //context

    private static final String DATA_BASE_NAME = "DataBase.bd"; //name

    private static DataBase dataBase; //class

    private ArrayList<Task> taskArrayList; //return list

    //private int level = -1; //return level

    private ArrayList<Task> historyTaskArrayList;

    /**construct database
     * @param context
     **/
    private DataBase(Context context){
        this.context = context;
        dbHelper = new DataBaseHelper(context, DATA_BASE_NAME, null, VERSION);
        taskTables = dbHelper.getWritableDatabase();
        //LoadData loadData = new LoadData();
        //loadData.run();
    }

    /**return database manager
     * @param context
     * @return dataBase
     * **/
    public static DataBase getDataBase(Context context){
        if(dataBase == null){
            // avoid many thread create this singleton
            synchronized (DataBase.class){
                if(dataBase == null){
                    dataBase = new DataBase(context);
                }
            }
        }
        return dataBase;
    }

//    /**
//     * provide level
//     * @return current level
//     */
//    public int getLevel(){
//        Cursor cursor = taskTables.query(DataBaseHelper.LEVEL_TABLE_NAME,null,null,null,null,null,null,null);
//        if(cursor.moveToFirst()){
//            do{
//                level = cursor.getInt(cursor.getColumnIndex("level"));
//            }while (cursor.moveToNext());
//        }
//        cursor.close();
//        return level;
//    }
//
//    /**
//     * set variable level
//     * @param level
//     */
//    public void setLevel(int level) {
//        if(getLevel()==-1){
//            levelCreate(level);
//        }else{
//            ContentValues contentValues = new ContentValues();
//            contentValues.put("level",level);
//            taskTables.update(DataBaseHelper.LEVEL_TABLE_NAME, contentValues,
//                    "level = ?", new String[]{String.valueOf(level)});
//        }
//        this.level = level;
//    }
//
//    /**
//     * create level
//     */
//    private void levelCreate(int level){
//        ContentValues contentValues =new ContentValues();
//        contentValues.put("level",level);
//        taskTables.insert(DataBaseHelper.LEVEL_TABLE_NAME,null,contentValues);
//    }

    /**
     * add task in database
     * @param task the task which need to be added
     * @return new task id
     */

    public int createTask(Task task){
        ContentValues contentValues = new ContentValues();

        //contentValues input
        contentValues.put("task_name", task.getTaskName());
        contentValues.put("task_start_date", task.getTaskStartDate());
        contentValues.put("task_start_time", task.getTaskStartTime());
        contentValues.put("task_complete_date", task.getTaskCompleteDate());
        contentValues.put("task_complete_time", task.getTaskCompleteTime());
        contentValues.put("task_place", task.getTaskPlace());
        contentValues.put("task_memo", task.getTaskMemo());
        contentValues.put("is_complete", false);
        contentValues.put("is_complete_in_time", false);

        taskTables.insert(DataBaseHelper.TABLE_NAME,null, contentValues);

        return findNewAddTaskId(task);
    }

    /**
     * find all elements in table
     * @return ArrayList loaded from database
     */
    public ArrayList retrieve(){
        ArrayList<Task> arrayList = new ArrayList<>();
        Cursor cursor = taskTables.query(DataBaseHelper.TABLE_NAME,null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                Task task = getTaskByCursor(cursor);
                arrayList.add(task);
                arrayList = sortArrayList(arrayList);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return arrayList;
    }

    /**
     * use cursor find thing
     * @param cursor
     * @return
     */
    private Task getTaskByCursor(Cursor cursor){
        return new Task(cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("task_name")),
                cursor.getString(cursor.getColumnIndex("task_start_date")),
                cursor.getString(cursor.getColumnIndex("task_start_time")),
                cursor.getString(cursor.getColumnIndex("task_complete_date")),
                cursor.getString(cursor.getColumnIndex("task_complete_time")),
                cursor.getString(cursor.getColumnIndex("task_real_complete_date")),
                cursor.getString(cursor.getColumnIndex("task_real_complete_time")),
                cursor.getString(cursor.getColumnIndex("task_place")),
                cursor.getString(cursor.getColumnIndex("task_real_memo")),
                (cursor.getInt(cursor.getColumnIndex("is_complete")) == 1),
                (cursor.getInt(cursor.getColumnIndex("is_complete_in_time")) == 1));
    }

    /**
     * find taskId in database
     * @param task the task wanting to find
     * @return index in database
     */
    private int findNewAddTaskId(Task task){
        int taskId = 0;
        Cursor cursor = taskTables.query(DataBaseHelper.TABLE_NAME,new String[]{"id"},null,null,null,null,null);
        if(cursor.moveToLast()){
            taskId = cursor.getInt(cursor.getColumnIndex("id"));
        }
        cursor.close();
        return taskId;
    }

    /**
     * find task by id
     * @param id task which you want to find
     * @return found task
     */
    public Task getTaskById(int id){
        Cursor cursor = taskTables.query(DataBaseHelper.TABLE_NAME,null,null,null,null,null,null);
        Task task = null;
        if(cursor.moveToFirst()){
            do{
                int taskId = cursor.getInt(cursor.getColumnIndex("id"));
                if(taskId == id){
                    task = getTaskByCursor(cursor);
                }
            }while(cursor.moveToNext());
        }
        cursor.close();
        return task;
    }


    /**
     * modify task in database
     * @param task the task needed to be update
     */
    public void updateTask(Task task){

        //modify difference
        ContentValues contentValues = new ContentValues();
        contentValues.put("task_name",task.getTaskName());
        contentValues.put("task_start_date", task.getTaskStartDate());
        contentValues.put("task_start_time", task.getTaskStartTime());
        contentValues.put("task_complete_date", task.getTaskCompleteDate());
        contentValues.put("task_complete_time", task.getTaskCompleteTime());
        contentValues.put("task_real_complete_date", task.getTaskRealCompleteDate());
        contentValues.put("task_real_complete_time", task.getTaskRealCompleteTime());
        contentValues.put("task_place", task.getTaskPlace());
        contentValues.put("task_memo", task.getTaskMemo());
        contentValues.put("is_complete", task.isCompleted());
        contentValues.put("is_complete_in_time", task.isCompletedInTime());

        taskTables.update(DataBaseHelper.TABLE_NAME, contentValues,
                "id=?", new String[]{String.valueOf(task.getTaskId())});
        //updateArrayList("up",task);
    }

    /**
     *delete task in database
     * @param task the task needed to be deleted
     */
    public void deleteTask(Task task){
        taskTables.delete(DataBaseHelper.TABLE_NAME,
                "id = ?", new String[]{String.valueOf(task.getTaskId())});
        //updateArrayList("del",task);
        //return taskArrayList;
    }

    public ArrayList getAllDate(){
        Cursor cursor = taskTables.query(DataBaseHelper.TABLE_NAME,new String[]{"task_start_date"},null,null,null,null,null);
        ArrayList<String> res = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                res.add(cursor.getString(cursor.getColumnIndex("task_start_date")));
            }while(cursor.moveToNext());
        }
        return sortDateArrayList(res);
    }

    private ArrayList sortDateArrayList(ArrayList<String> arrayList){
        // Date格式是yyyy/MM/dd
        for(int i = 0; i < arrayList.size() ; i++){
            for(int j = i + 1 ; j < arrayList.size() ; j++){

            }
        }
        return arrayList;
    }

    public ArrayList getTasksByDate(String taskStartDate){
        Cursor cursor = taskTables.query(DataBaseHelper.TABLE_NAME, null, "task_start_date = ?", new String[]{ taskStartDate }, null, null, null);
        ArrayList<Task> res = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                res.add(getTaskByCursor(cursor));
            }while(cursor.moveToNext());
        }
        return res;
    }

    /**
     * sort ArrayList by
     * @param arrayList ArrayList need to be edit
     */
    private ArrayList sortArrayList(ArrayList<Task> arrayList){
        // Time格式是HH:mm

        return arrayList;
    }

//    public ArrayList getTaskList(){
//        return taskArrayList;
//    }

//    /**
//     * get id list in database
//     * @param date the number between today and that day
//     * @return String tuple
//     */
//    private String[] getIdList(int date){
//        String res = "";
//        Cursor cursor = taskTables.query(DataBaseHelper.HISTORY_TABLE_NAME,null,null,null,null,null,null);
//        if(cursor.moveToFirst()){
//            do{
//                int tem = cursor.getInt(cursor.getColumnIndex("date"));
//                if(date==tem){
//                    res = cursor.getString(cursor.getColumnIndex("ids_str"));
//                }
//            }while(cursor.moveToNext());
//        }
//        cursor.close();
//        return res.split(";");
//    }


//    /**get day which they want
//     * @param date the number between today and that day
//     * @return
//     */
//    public ArrayList getHistoryTable(int date){
//        String[] ids = getIdList(date);
//        for(int i = 0;i < ids.length;i++){
//            Task task = getTaskById(Integer.parseInt(ids[i]));
//            historyTaskArrayList.add(task);
//            sortArrayList(historyTaskArrayList);
//        }
//        return historyTaskArrayList;
//    }

//    /**
//     * set data in database
//     * @param date thing need to input
//     */
//    public void setHistoryTable(int date){
//        ContentValues contentValues = new ContentValues();
//        ArrayList<Task> arrayList = retrieve();
//        String str = "";
//        for (int i = 0;i < arrayList.size();i++){
////            if(arrayList.get(i).isRepeat()){
////                str = str + arrayList.get(i).getTaskId() + ";";
////            }
//        }
//        str = str.substring(1,str.length()-1);
//        contentValues.put("date",date);
//        contentValues.put("ids_str",str);
//        taskTables.insert(DataBaseHelper.HISTORY_TABLE_NAME,null, contentValues);
//    }


//    /**
//     * Thread class
//     */
//    class LoadData implements Runnable{
//        @Override
//        public void run() {
//            taskArrayList = retrieve();
//            //getLevel();
//        }
//    }
}
