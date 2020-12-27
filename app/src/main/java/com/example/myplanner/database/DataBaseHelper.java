package com.example.myplanner.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    /**
     class Task{
        String 	taskName
        String 	taskStartDate
        String 	taskStartTime
        String 	taskCompleteDate
        String	taskCompleteTime
        String	taskRealCompleteDate
        String	taskRealCompleteTime
        String	taskPlace
        String	taskMemo
        boolean isCompleted			//该任务是不是完成？
        boolean	isCompletedInTime 	//该任务是不是在制定的时间内完成？
     }
     **/
    public static final String CREATE_TASK = "create table TaskTable"
            + "("
            + "id int(11) PRIMARY KEY AUTOINCREMENT NOT NULL ,"
            + "task_name varchar(255) DEFAULT NULL,"
            + "task_start_date varchar(255) DEFAULT NULL,"
            + "task_start_time varchar(255) DEFAULT NULL,"
            + "task_complete_date varchar(255) DEFAULT NULL,"
            + "task_complete_time varchar(255) DEFAULT NULL,"
            + "task_real_complete_date varchar(255) DEFAULT NULL,"
            + "task_real_complete_time varchar(255) DEFAULT NULL,"
            + "task_place varchar(255) DEFAULT NULL,"
            + "task_memo varchar(255) DEFAULT NULL,"
            + "is_complete boolean NOT NULL,"
            + "is_complete_in_time boolean NOT NULL"
            + ")";

    public static final String CREATE_LEVEL = "create table level_table"
            + "("
            + "level int"
            + ")";


    public static final String HISTORY_TABLE_NAME = "history_table";
    public static final String LEVEL_TABLE_NAME = "level_table";
    public static final String TABLE_NAME = "task_table";

    //带全部参数的构造函数, 此构造函数必不可少, name为数据库名称
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TASK);
        db.execSQL(CREATE_LEVEL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (newVersion){
            case 1:

            case 2:
                db.execSQL(CREATE_LEVEL);
            case 3:
                break;
        }
    }
}
