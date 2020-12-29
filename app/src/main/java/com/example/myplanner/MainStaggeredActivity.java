package com.example.myplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.myplanner.adapter.StaggeredCardAdapter;
import com.example.myplanner.dataHelper.Converter;
import com.example.myplanner.dataHolder.CardViewData;
import com.example.myplanner.database.DataBase;
import com.example.myplanner.decorator.CustomItemDecorator;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainStaggeredActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView emptyView;
    StaggeredCardAdapter cardAdapter;
    List<CardViewData> dataItems;
    FloatingActionButton floatingActionButton;
    BottomAppBar backButton;
    DataBase dataBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_staggered_grid);

        dataBase = DataBase.getDataBase(this);
        recyclerView = findViewById(R.id.dayTaskRecyclerView);
        emptyView = findViewById(R.id.empty_view);
        dataItems = new ArrayList<>();
        initData();
        if(dataItems.size()==0){
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }else{
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }

        setCardAdapter();

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
                finish();
            }
        });
    }

    public void openAddTaskActivity(){
        Intent intent = new Intent(this, AddTaskActivity.class);
        startActivity(intent);
    }

    public void setCardAdapter(){

        cardAdapter = new StaggeredCardAdapter(dataItems, this);
        recyclerView.setAdapter(cardAdapter);
        recyclerView.addItemDecoration(new CustomItemDecorator(50));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }


    public void initData(){

        ArrayList<String> allDate = dataBase.getAllDate();
        String day_Date;
        for(int i=0; i<allDate.size(); i++){
            day_Date = allDate.get(i);
            dataItems.add( Converter.toCardViewData(day_Date, dataBase.getTasksByDate(day_Date)) );
        }

    }
}