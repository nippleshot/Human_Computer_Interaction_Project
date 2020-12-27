package com.example.myplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.myplanner.decorator.CustomItemDecorator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainStaggeredActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    StaggeredCardAdapter cardAdapter;
    List<TempCard> dataItems;
    FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_staggered_grid);

        recyclerView = findViewById(R.id.dayTaskRecyclerView);
        initData();
        setCardAdapter();

        floatingActionButton = (FloatingActionButton) findViewById(R.id.makeNewTask);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddTaskActivity();
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
        dataItems = new ArrayList<>();

        String[] date = {"2020年12月23日", "2020年12月24日" ,"2020年12月25日" ,"2020年12月26日" ,"2020年12月27日" ,"2020年12月28日" ,"2020年12月29日" ,"2020年12月30日" ,"2020年12月31日"};
        int[] total = {10, 12, 19, 21, 10, 11, 13, 14, 17};
        int[] notCompleted = {0, 10, 0, 10, 0, 0, 10, 0, 0, 1};
        int[] efficiency = {-120, -231, 10, 68, 90, -10, -92, 31, 12};

        for(int i=0; i<date.length; i++){
            dataItems.add(new TempCard(date[i], total[i], efficiency[i], notCompleted[i]));
        }
    }
}