package com.example.myplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myplanner.adapter.TaskListAdapter;
import com.example.myplanner.database.DataBase;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class OneDayTaskActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DataBase dataBase;
    ArrayList<Task> day_Tasks;
    TaskListAdapter taskListAdapter;
    FloatingActionButton floatingActionButton;
    BottomAppBar backButton;

//    List<TempTask> tempTaskList;
//    List<TempCompletedTask> completedTasks;
//    List<DataItem> dataItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_day_task);

        String day_Date = getIntent().getExtras().getString("day_Date");

        dataBase = DataBase.getDataBase(this);
        day_Tasks = dataBase.getTasksByDate(day_Date);
        recyclerView = findViewById(R.id.taskRecyclerView);
//        initTempTask();
//        initData();
        setRecycleView();


        floatingActionButton = (FloatingActionButton) findViewById(R.id.makeNewTask);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddTaskActivity();
            }
        });

        backButton = findViewById(R.id.bottomAppBar);
        backButton.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void openAddTaskActivity(){
        Intent intent = new Intent(this, AddTaskActivity.class);
        startActivity(intent);
    }

    public void removeItem(int position) {
        dataItemList.remove(position);
        taskListAdapter.notifyItemRemoved(position);
    }

    public void insertItem(TempCompletedTask tempCompletedTask) {
        completedTasks.add(tempCompletedTask);
        taskListAdapter.completedTaskAdapter.notifyDataSetChanged();
    }


    private void setRecycleView() {
        taskListAdapter = new TaskListAdapter(day_Tasks, this);
        recyclerView.setAdapter(taskListAdapter);
        recyclerView.setHasFixedSize(true);

        taskListAdapter.setOnItemClickListener(new TaskListAdapter.OnItemClickListener() {

            @Override
            public void onDeleteClick(int position) {
                // 데이터베이스 안에 TasK를 삭제하기
                // taskListAdapter 한테 day_Tasks가 변경됬다고 알리기
                DataItem removing_item = dataItemList.get(position);
                removeItem(position);


                Snackbar snackbar =  Snackbar.make(recyclerView, "确定删除该任务?", Snackbar.LENGTH_LONG).setAction("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 데이터베이스 안에 TasK를 추가하기
                        // taskListAdapter 한테 day_Tasks가 변경됬다고 알리기
                        dataItemList.add(position, removing_item);
                        taskListAdapter.notifyItemInserted(position);
                        Snackbar snackbar1 = Snackbar.make(recyclerView, "没删除了", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                    }
                });

                snackbar.show();

            }

            @Override
            public void onInsertClick(int position) {
                // 1. 데이터베이스 안에 Task.taskRealCompleteDate&Time에 현재시간 추가
                // 2. 데이터베이스 안에 Task.isCompleted를 true로 변경
                // 3. 데이터베이스 안에 Task.isCompletedInTime를 TaskHelper.subtractDateTime 를 통해 true나 false로 설정
                // 4. taskListAdapter 한테 day_Tasks가 변경됬다고 알리기

                DataItem completed_item = dataItemList.get(position);
                removeItem(position);
                insertItem(new TempCompletedTask(completed_item.getTaskInfo().getTaskTitle(), "NaN-NaN", (int) Math.random()));

                Snackbar snackbar =  Snackbar.make(recyclerView, "确定完成了该任务?", Snackbar.LENGTH_LONG).setAction("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 1. 데이터베이스 안에 Task.taskRealCompleteDate&Time 삭제
                        // 2. 데이터베이스 안에 Task.isCompleted를 false로 변경
                        // 3. 데이터베이스 안에 Task.isCompletedInTime를 false로 변경
                        // 4. taskListAdapter 한테 day_Tasks가 변경됬다고 알리기

                        dataItemList.add(position, completed_item);
                        taskListAdapter.notifyItemInserted(position);

                        int position = completedTasks.size()-1;
                        completedTasks.remove(position);
                        taskListAdapter.completedTaskAdapter.notifyItemRemoved(position);

                        Snackbar snackbar1 = Snackbar.make(recyclerView, "没表示为完成了", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                    }
                });
                snackbar.show();


            }
        });
    }


//    private void initTempTask() {
//
//        tempTaskList = new ArrayList<>();
//
//        String[] title = {"起床", "上人机交互系统课", "吃午饭", "购买水果", "打球",
//                        "人机交互大作业会议", "软件质量与过程小论文", "看电影", "晚上约定", "睡觉"};
//        int[] timeLeft = {10, 30, 130, 252, 280, 375, 431, 593, 654 ,738};
//        String[] place = {"无", "鼓楼校区：教211", "第三食堂", "无", "仙林校区：第四组团",
//                        "无", "无", "紫峰大厦电影院", "牛先生鲜牛肉火锅（三牌楼总店）", "无"};
//        String[] memo = {"无", "无", "无", "优惠券：https://www.ticketmaster.co.uk/", "球没有气，买饮料15瓶",
//                "腾讯会议ID：515 986 7577", "无", "一点就到家 PM4:30", "点评：https://www.meituan.com/meishi/95863604/", "无"};
//
//        for(int i=0; i<title.length; i++){
//            tempTaskList.add(new TempTask(i, false, title[i], timeLeft[i], place[i], memo[i]));
//        }
//
//
//
//    }

//    private void initData(){
//        dataItemList = new ArrayList<>();
//
//        completedTasks = new ArrayList<>();
//        completedTasks.add(new TempCompletedTask("洗澡", "08:20 - 09:13", -42));
//        completedTasks.add(new TempCompletedTask("吃早餐", "09:10 - 09:35", 92));
//        completedTasks.add(new TempCompletedTask("完成小作业", "09:40 - 10:12", -10));
//
//        TempHead headInfo = new TempHead("2020年12月23日", 13, 3,2,10, completedTasks);
//
//        dataItemList.add(new DataItem(headInfo, null, 1));
//
//        for(int i=0; i<tempTaskList.size(); i++){
//            dataItemList.add(new DataItem(null, tempTaskList.get(i), 2));
//        }
//
//    }




}