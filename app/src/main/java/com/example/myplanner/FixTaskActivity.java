package com.example.myplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.myplanner.dataHelper.TaskHelper;
import com.example.myplanner.database.DataBase;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class FixTaskActivity extends AppCompatActivity {

    TextInputLayout taskName_inputLayout, taskStartDate_inputLayout, taskStartTime_inputLayout, taskCompleteDate_inputLayout, taskCompleteTime_inputLayout,taskPlace_inputLayout;
    TextInputEditText taskMemo_inputLayout;
    TimePickerDialog s_timePickerDialog, c_timePickerDialog;
    DatePickerDialog s_datePickerDialog, c_datePickerDialog;
    MaterialButton button;
    BottomAppBar backButton;
    DataBase dataBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_task);

        dataBase = DataBase.getDataBase(this);
        int task_db_Id = getIntent().getIntExtra("task_db_Id", Integer.MAX_VALUE);
        Task to_FixTask = dataBase.getTaskById(task_db_Id);


        taskName_inputLayout = findViewById(R.id.fix_text_input_taskName);
        taskStartDate_inputLayout = findViewById(R.id.fix_text_input_taskStartDate);
        taskStartTime_inputLayout = findViewById(R.id.fix_text_input_taskStartTime);
        taskCompleteDate_inputLayout = findViewById(R.id.fix_text_input_taskCompleteDate);
        taskCompleteTime_inputLayout = findViewById(R.id.fix_text_input_taskCompleteTime);
        taskPlace_inputLayout = findViewById(R.id.fix_text_input_taskPlace);
        taskMemo_inputLayout = findViewById(R.id.fix_text_input_taskMemo);

        EditText startDate = taskStartDate_inputLayout.getEditText();
        EditText startTime = taskStartTime_inputLayout.getEditText();
        EditText completeDate = taskCompleteDate_inputLayout.getEditText();
        EditText completeTime = taskCompleteTime_inputLayout.getEditText();


        taskName_inputLayout.getEditText().setText(to_FixTask.getTaskName());
        taskPlace_inputLayout.getEditText().setText(to_FixTask.getTaskPlace());
        taskMemo_inputLayout.setText(to_FixTask.getTaskMemo());
        startDate.setText(to_FixTask.getTaskStartDate());
        startTime.setText(to_FixTask.getTaskStartTime());
        completeDate.setText(to_FixTask.getTaskCompleteDate());
        completeTime.setText(to_FixTask.getTaskCompleteTime());

        //Date == yyyy[0] MM[1] dd[2]
        //Time ==   HH[0] mm[1]
        String[] origin_StartDate = to_FixTask.getTaskStartDate().split("/");
        String[] origin_StartTime = to_FixTask.getTaskStartTime().split(":");
        String[] origin_CompleteDate = to_FixTask.getTaskCompleteDate().split("/");
        String[] origin_CompleteTime = to_FixTask.getTaskCompleteTime().split(":");

        //Log.i("FixTaskActivity", "StartDate ==> " + origin_StartDate[0] + origin_StartDate[1] + origin_StartDate[2] );


        backButton = findViewById(R.id.bottomAppBar);
        backButton.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openOneDayTaskActivity(to_FixTask.getTaskStartDate());
                finish();
            }
        });

        button = findViewById(R.id.updateNewTask);





        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();

                s_datePickerDialog = new DatePickerDialog(FixTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String printMonth = Integer.toString(month);
                        String printDay = Integer.toString(day);
                        if(printMonth.length()==1){
                            printMonth = "0"+printMonth;
                        }
                        if(printDay.length()==1) {
                            printDay = "0" + printDay;
                        }

                        String date = year+"/"+printMonth+"/"+printDay;
                        startDate.setText(date);
                    }
                }, Integer.parseInt(origin_StartDate[0]), Integer.parseInt(origin_StartDate[1])-1, Integer.parseInt(origin_StartDate[2]));
                s_datePickerDialog.show();
            }
        });


        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();



                s_timePickerDialog = new TimePickerDialog(FixTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {

                        String printHour = Integer.toString(hourOfDay);
                        String printMin = Integer.toString(minutes);
                        if(printHour.length()==1){
                            printHour = "0"+printHour;
                        }
                        if(printMin.length()==1){
                            printMin = "0"+printMin;
                        }
                        String printFull = printHour + ":" + printMin;

                        startTime.setText(printFull);
                    }
                }, Integer.parseInt(origin_StartTime[0]), Integer.parseInt(origin_StartTime[1]), false);

                s_timePickerDialog.show();
            }
        });

        completeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();

                c_datePickerDialog = new DatePickerDialog(FixTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String printMonth = Integer.toString(month);
                        String printDay = Integer.toString(day);
                        if(printMonth.length()==1){
                            printMonth = "0"+printMonth;
                        }
                        if(printDay.length()==1) {
                            printDay = "0" + printDay;
                        }

                        String date = year+"/"+printMonth+"/"+printDay;
                        completeDate.setText(date);
                    }
                }, Integer.parseInt(origin_CompleteDate[0]), Integer.parseInt(origin_CompleteDate[1])-1, Integer.parseInt(origin_CompleteDate[2]));

                c_datePickerDialog.show();
            }
        });

        completeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();


                c_timePickerDialog = new TimePickerDialog(FixTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {

                        String printHour = Integer.toString(hourOfDay);
                        String printMin = Integer.toString(minutes);
                        if(printHour.length()==1){
                            printHour = "0"+printHour;
                        }
                        if(printMin.length()==1){
                            printMin = "0"+printMin;
                        }
                        String printFull = printHour + ":" + printMin;

                        completeTime.setText(printFull);
                    }
                }, Integer.parseInt(origin_CompleteTime[0]), Integer.parseInt(origin_CompleteTime[1]), false);

                c_timePickerDialog.show();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(taskName_inputLayout.getEditText().getText().toString().equals("")
                        || taskStartDate_inputLayout.getEditText().getText().toString().equals("")
                        || taskStartTime_inputLayout.getEditText().getText().toString().equals("")
                        || taskCompleteDate_inputLayout.getEditText().getText().toString().equals("")
                        || taskCompleteTime_inputLayout.getEditText().getText().toString().equals("") ) {

                    if(taskName_inputLayout.getEditText().getText().toString().equals("")){
                        taskName_inputLayout.setError("这里不能为空");
                    }
                    if(taskStartDate_inputLayout.getEditText().getText().toString().equals("")){
                        taskStartDate_inputLayout.setError("这里不能为空");
                    }
                    if(taskStartTime_inputLayout.getEditText().getText().toString().equals("")){
                        taskStartTime_inputLayout.setError("这里不能为空");
                    }
                    if(taskCompleteDate_inputLayout.getEditText().getText().toString().equals("")){
                        taskCompleteDate_inputLayout.setError("这里不能为空");
                    }
                    if(taskCompleteTime_inputLayout.getEditText().getText().toString().equals("")){
                        taskCompleteTime_inputLayout.setError("这里不能为空");
                    }

                }else if( TaskHelper.subtractDateTime(
                        taskCompleteDate_inputLayout.getEditText().getText().toString()+taskCompleteTime_inputLayout.getEditText().getText(),
                        taskStartDate_inputLayout.getEditText().getText().toString()+taskStartTime_inputLayout.getEditText().getText().toString()) <= 0){

                    if(taskCompleteDate_inputLayout.getEditText().getText().toString().equals( taskStartDate_inputLayout.getEditText().getText().toString() )){
                        taskCompleteTime_inputLayout.setError("比开始时间更早");
                    }else{
                        taskCompleteDate_inputLayout.setError("比开始日期更早");
                        taskCompleteTime_inputLayout.setError("比开始时间更早");
                    }


                } else{
                    Task updateTask = new Task(
                            to_FixTask.getTaskId(),
                            taskName_inputLayout.getEditText().getText().toString(),
                            taskStartDate_inputLayout.getEditText().getText().toString(),
                            taskStartTime_inputLayout.getEditText().getText().toString(),
                            taskCompleteDate_inputLayout.getEditText().getText().toString(),
                            taskCompleteTime_inputLayout.getEditText().getText().toString(),
                            to_FixTask.getTaskRealCompleteDate(),
                            to_FixTask.getTaskRealCompleteTime(),
                            taskPlace_inputLayout.getEditText().getText().toString(),
                            taskMemo_inputLayout.getText().toString(),
                            to_FixTask.isCompleted(),
                            to_FixTask.isCompletedInTime()
                    );

                    dataBase.updateTask( updateTask );

                    closeKeyboard();

                    openOneDayTaskActivity( updateTask.getTaskStartDate() );

                    finish();

                }

            }
        });

    }

    public void openMainStaggeredActivity(){
        Intent intent = new Intent(this, MainStaggeredActivity.class);
        startActivity(intent);
    }

    public void openOneDayTaskActivity(String date){
        Intent intent = new Intent(this, OneDayTaskActivity.class);
        intent.putExtra("day_Date", date);
        startActivity(intent);
    }

    private void closeKeyboard(){
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}