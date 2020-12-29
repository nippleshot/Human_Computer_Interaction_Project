package com.example.myplanner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myplanner.adapter.CompletedTaskAdapter;
import com.example.myplanner.adapter.TaskListAdapter;
import com.example.myplanner.dataHelper.Converter;
import com.example.myplanner.dataHelper.TaskHelper;
import com.example.myplanner.dataHolder.RecycleViewData;
import com.example.myplanner.database.DataBase;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OneDayTaskActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DataBase dataBase;
    ArrayList<Task> day_Tasks;
    String day_Date;
    TaskListAdapter taskListAdapter;
    FloatingActionButton floatingActionButton;
    BottomAppBar backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_day_task);

        day_Date = getIntent().getExtras().getString("day_Date");

        dataBase = DataBase.getDataBase(this);
        day_Tasks = dataBase.getTasksByDate(day_Date);
        recyclerView = findViewById(R.id.taskRecyclerView);

//        Log.i("OneDayTaskActivity", "(Before setRecycleview) getIntent().getExtras() ==> " + day_Date );
//        Log.i("OneDayTaskActivity", "(Before setRecycleview) day_Tasks.size ==> " + day_Tasks.size() );
        setRecycleView();


        floatingActionButton = (FloatingActionButton) findViewById(R.id.makeNewTask);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddTaskActivity();
                finish();
            }
        });

        backButton = findViewById(R.id.bottomAppBar);
        backButton.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainTaskActivity();
                finish();
            }
        });
    }

    public void openAddTaskActivity(){
        Intent intent = new Intent(this, AddTaskActivity.class);
        startActivity(intent);
    }

    public void openMainTaskActivity(){
        Intent intent = new Intent(this, MainStaggeredActivity.class);
        startActivity(intent);
    }




    private void setRecycleView() {
        taskListAdapter = new TaskListAdapter(day_Tasks, this);

        recyclerView.setAdapter(taskListAdapter);
        recyclerView.setHasFixedSize(true);

        taskListAdapter.setOnItemClickListener(new TaskListAdapter.OnItemClickListener() {

            @Override
            public void onDeleteClick(int task_db_Id, RecycleViewData recycleViewData, int position) {
                Task tempTask = dataBase.getTaskById(task_db_Id);


                // 데이터베이스 안에 TasK를 삭제하기
                // taskListAdapter 한테 day_Tasks가 변경됬다고 알리기
                dataBase.deleteTask(new Task(task_db_Id));

                taskListAdapter.dataItems.remove(position);
                taskListAdapter.notifyItemRemoved(position);
                taskListAdapter.dataItems.get(0).getAnalysisViewData().update_delete();
                taskListAdapter.notifyItemChanged(0);


                Snackbar snackbar =  Snackbar.make(recyclerView, "确定删除该任务?", Snackbar.LENGTH_LONG).setAction("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 데이터베이스 안에 TasK를 추가하기
                        // taskListAdapter 한테 day_Tasks가 변경됬다고 알리기
                        int new_db_id = dataBase.createTask(tempTask);
                        recycleViewData.getCheckListViewData().setTask_db_index(new_db_id);
                        taskListAdapter.dataItems.add(position, recycleViewData);
                        taskListAdapter.notifyItemInserted(position);
                        taskListAdapter.dataItems.get(0).getAnalysisViewData().update_undelete();
                        taskListAdapter.notifyItemChanged(0);

                        Snackbar snackbar1 = Snackbar.make(recyclerView, "没删除了", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                    }
                });

                snackbar.show();

            }

            @Override
            public void onInsertClick(int task_db_Id, RecycleViewData recycleViewData, int position) {
                Task tempTask = dataBase.getTaskById(task_db_Id);

                // 1. 데이터베이스 안에 Task.taskRealCompleteDate&Time에 현재시간 추가
                // 2. 데이터베이스 안에 Task.isCompleted를 true로 변경
                // 3. 데이터베이스 안에 Task.isCompletedInTime를 TaskHelper.subtractDateTime 를 통해 true나 false로 설정
                // 4. taskListAdapter 한테 day_Tasks가 변경됬다고 알리기

                Date current = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd;HH:mm");
                String[] currentDate_Time = dateFormat.format(current).split(";");
                if(!tempTask.isCompleted()){
                    tempTask.setTaskRealCompleteDate(currentDate_Time[0]);
                    tempTask.setTaskRealCompleteTime(currentDate_Time[1]);
                    tempTask.setCompleted(true);
                    if( TaskHelper.countGapInMin(tempTask) < 0 ){
                        tempTask.setCompletedInTime( false );
                    }else{
                        tempTask.setCompletedInTime( true );
                    }
                }

                int tempTaskEfficiency = TaskHelper.countGapInMin(tempTask);
                dataBase.updateTask(tempTask);


                taskListAdapter.dataItems.remove(position);
                taskListAdapter.notifyItemRemoved(position);
                taskListAdapter.dataItems.get(0).getAnalysisViewData().update( tempTask );
                taskListAdapter.notifyItemChanged(0);
                taskListAdapter.completedTaskAdapter.notifyItemChanged(0);


                Snackbar snackbar =  Snackbar.make(recyclerView, "确定完成了该任务?", Snackbar.LENGTH_LONG).setAction("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 1. 데이터베이스 안에 Task.taskRealCompleteDate&Time 삭제
                        // 2. 데이터베이스 안에 Task.isCompleted를 false로 변경
                        // 3. 데이터베이스 안에 Task.isCompletedInTime를 false로 변경
                        // 4. taskListAdapter 한테 day_Tasks가 변경됬다고 알리기

                        tempTask.setTaskRealCompleteDate(null);
                        tempTask.setTaskRealCompleteTime(null);
                        tempTask.setCompleted( false );
                        tempTask.setCompletedInTime( false );

                        dataBase.updateTask(tempTask);

                        taskListAdapter.dataItems.add(position, recycleViewData);
                        taskListAdapter.notifyItemInserted(position);
                        taskListAdapter.dataItems.get(0).getAnalysisViewData().downdate(tempTask, tempTaskEfficiency);
                        taskListAdapter.notifyItemChanged(0);
                        taskListAdapter.completedTaskAdapter.notifyDataSetChanged();


                        Snackbar snackbar1 = Snackbar.make(recyclerView, "没表示为完成了", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                    }
                });

                snackbar.show();


            }
        });

//        taskListAdapter.completedTaskAdapter.setOnCompleteItemClickListener(new CompletedTaskAdapter.OnCompleteItemClickListener() {
//            @Override
//            public void onCancelClick(int task_db_Id) {
//                Task tempTask = dataBase.getTaskById(task_db_Id);
//                int tempTaskEfficiency = TaskHelper.countGapInMin(tempTask);
//
//                tempTask.setTaskRealCompleteDate(null);
//                tempTask.setTaskRealCompleteTime(null);
//                tempTask.setCompleted( false );
//                tempTask.setCompletedInTime( false );
//
//                dataBase.updateTask(tempTask);
//
//                taskListAdapter.dataItems.add( Converter.toRecycleViewData(tempTask) );
//                taskListAdapter.notifyDataSetChanged();
//                taskListAdapter.dataItems.get(0).getAnalysisViewData().downdate(tempTask, tempTaskEfficiency);
//                taskListAdapter.notifyItemChanged(0);
//                taskListAdapter.completedTaskAdapter.notifyDataSetChanged();
//
//            }
//        });
    }



}