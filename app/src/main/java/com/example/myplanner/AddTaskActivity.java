package com.example.myplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class AddTaskActivity extends AppCompatActivity {
    TextInputLayout taskName_inputLayout, taskStartDate_inputLayout, taskStartTime_inputLayout, taskCompleteDate_inputLayout, taskCompleteTime_inputLayout,taskPlace_inputLayout;
    TextInputEditText taskMemo_inputLayout;
    TimePickerDialog s_timePickerDialog, c_timePickerDialog;
    DatePickerDialog s_datePickerDialog, c_datePickerDialog;
    MaterialButton button;
    BottomAppBar backButton;
    Calendar calendar;
    int currentYear;
    int currentMonth;
    int currentDay;
    int currentHour;
    int currentMinute;

    DataBase dataBase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskName_inputLayout = findViewById(R.id.text_input_taskName);
        taskStartDate_inputLayout = findViewById(R.id.text_input_taskStartDate);
        taskStartTime_inputLayout = findViewById(R.id.text_input_taskStartTime);
        taskCompleteDate_inputLayout = findViewById(R.id.text_input_taskCompleteDate);
        taskCompleteTime_inputLayout = findViewById(R.id.text_input_taskCompleteTime);
        taskPlace_inputLayout = findViewById(R.id.text_input_taskPlace);
        taskMemo_inputLayout = findViewById(R.id.text_input_taskMemo);

        dataBase = DataBase.getDataBase(this);
        button = findViewById(R.id.addNewTask);

        backButton = findViewById(R.id.bottomAppBar);
        backButton.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainStaggeredActivity();
                finish();
            }
        });


        EditText startDate = taskStartDate_inputLayout.getEditText();
        EditText startTime = taskStartTime_inputLayout.getEditText();
        EditText completeDate = taskCompleteDate_inputLayout.getEditText();
        EditText completeTime = taskCompleteTime_inputLayout.getEditText();


        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();
                calendar = Calendar.getInstance();
                currentYear = calendar.get(Calendar.YEAR);
                currentMonth = calendar.get(Calendar.MONTH);
                currentDay = calendar.get(Calendar.DAY_OF_MONTH);

                s_datePickerDialog = new DatePickerDialog(AddTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                }, currentYear, currentMonth, currentDay);
                s_datePickerDialog.show();
            }
        });


        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);


                s_timePickerDialog = new TimePickerDialog(AddTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                }, currentHour, currentMinute, false);

                s_timePickerDialog.show();
            }
        });

        completeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();
                calendar = Calendar.getInstance();
                currentYear = calendar.get(Calendar.YEAR);
                currentMonth = calendar.get(Calendar.MONTH);
                currentDay = calendar.get(Calendar.DAY_OF_MONTH);

                c_datePickerDialog = new DatePickerDialog(AddTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                }, currentYear, currentMonth, currentDay);

                c_datePickerDialog.show();
            }
        });

        completeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);


                c_timePickerDialog = new TimePickerDialog(AddTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                }, currentHour, currentMinute, false);

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
                    Task newTask = new Task(
                            taskName_inputLayout.getEditText().getText().toString(),
                            taskStartDate_inputLayout.getEditText().getText().toString(),
                            taskStartTime_inputLayout.getEditText().getText().toString(),
                            taskCompleteDate_inputLayout.getEditText().getText().toString(),
                            taskCompleteTime_inputLayout.getEditText().getText().toString(),
                            taskPlace_inputLayout.getEditText().getText().toString(),
                            taskMemo_inputLayout.getText().toString()
                    );

                    if(taskPlace_inputLayout.getEditText().getText().toString().equals("") ){
                        newTask.setTaskPlace("无");
                    }

                    if( taskMemo_inputLayout.getText().toString().equals("") ){
                        newTask.setTaskMemo("无");
                    }


                    dataBase.createTask( newTask );

                    closeKeyboard();

                    openOneDayTaskActivity( newTask.getTaskStartDate() );
                    finish();

                }
            }
        });

    }

    public void openOneDayTaskActivity(String date){
        Intent intent = new Intent(this, OneDayTaskActivity.class);
        intent.putExtra("day_Date", date);
        startActivity(intent);
    }

    public void openMainStaggeredActivity(){
        Intent intent = new Intent(this, MainStaggeredActivity.class);
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