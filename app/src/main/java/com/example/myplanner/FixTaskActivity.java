package com.example.myplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class FixTaskActivity extends AppCompatActivity {

    TextInputLayout taskName_inputLayout, taskStartDate_inputLayout, taskStartTime_inputLayout, taskCompleteDate_inputLayout, taskCompleteTime_inputLayout,taskPlace_inputLayout;
    EditText taskMemo_inputLayout;
    TimePickerDialog s_timePickerDialog, c_timePickerDialog;
    DatePickerDialog s_datePickerDialog, c_datePickerDialog;
    MaterialButton button;
    BottomAppBar backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_task);

        // int task_db_Id = getIntent().getIntExtra("task_db_Id", -1);
        int currentYear = 0, currentMonth = 0, currentDay = 0, currentHour = 0, currentMinute = 0; // just temp


        taskName_inputLayout = findViewById(R.id.fix_text_input_taskName);
        taskStartDate_inputLayout = findViewById(R.id.fix_text_input_taskStartDate);
        taskStartTime_inputLayout = findViewById(R.id.fix_text_input_taskStartTime);
        taskCompleteDate_inputLayout = findViewById(R.id.fix_text_input_taskCompleteDate);
        taskCompleteTime_inputLayout = findViewById(R.id.fix_text_input_taskCompleteTime);
        taskPlace_inputLayout = findViewById(R.id.fix_text_input_taskPlace);
        taskMemo_inputLayout = findViewById(R.id.fix_text_input_taskMemo);


        backButton = findViewById(R.id.bottomAppBar);
        backButton.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        button = findViewById(R.id.updateNewTask);


        EditText startDate = taskStartDate_inputLayout.getEditText();
        EditText startTime = taskStartTime_inputLayout.getEditText();
        EditText completeDate = taskCompleteDate_inputLayout.getEditText();
        EditText completeTime = taskCompleteTime_inputLayout.getEditText();


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
                }, currentYear, currentMonth, currentDay);
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
                }, currentHour, currentMinute, false);

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
                }, currentYear, currentMonth, currentDay);

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
                }, currentHour, currentMinute, false);

                c_timePickerDialog.show();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();
            }
        });

    }

    private void closeKeyboard(){
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}