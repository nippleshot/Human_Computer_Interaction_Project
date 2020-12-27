package com.example.myplanner.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myplanner.R;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class StaggeredCardAdapter extends RecyclerView.Adapter<StaggeredCardAdapter.DayTaskViewHolder>  {
    List<TempCard> dayTask;
    Context context;

    public StaggeredCardAdapter(List<TempCard> dayTask, Context context) {
        this.dayTask = dayTask;
        this.context = context;
    }

    @NonNull
    @Override
    public DayTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.day_review_card, parent, false);
        return new DayTaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayTaskViewHolder holder, int position) {
        TempCard tempCard = dayTask.get(position);

        holder.dayTaskDateYear.setText(tempCard.getDate().substring(0,5));
        holder.dayTaskDateMonth.setText(tempCard.getDate().substring(5));
        holder.totalDayTask.setText("总数 "+ tempCard.getTotal());

        int efficiencyMin = tempCard.getEfficiencyMin();

        //양수가 좋은거임 efficiencyMin = 목표시간(9시30분) - 리얼완성시간(9시15분)
        //음수일 경우    efficiencyMin = 목표시간(9시30분) - 리얼완성시간(9시45분)
        if(efficiencyMin < 0){
            holder.totalDayTaskEfficiency.setText(printSubtractedMin(efficiencyMin));
            holder.totalDayTaskEfficiency.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            holder.totalDayTaskEfficiency.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_trending_down_32_primary, 0, 0, 0);

        }else{
            holder.totalDayTaskEfficiency.setText(printSubtractedMin(efficiencyMin));
            holder.totalDayTaskEfficiency.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            holder.totalDayTaskEfficiency.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_trending_up_32_yellow, 0, 0, 0);
        }

        int notCompletedTaskNum = tempCard.getNotCompleted();
        if(notCompletedTaskNum > 0){
            holder.totalDayTaskNotComplete.setText("未完成 " + notCompletedTaskNum);
            holder.totalDayTaskNotComplete.setTextColor(ContextCompat.getColor(context, R.color.colorAccentVelvet));
            holder.dayTaskLinear_layout.setBackground(context.getResources().getDrawable(R.color.colorAccentVelvet));
            holder.dayTaskDateYear.setTextColor(ContextCompat.getColor(context, R.color.colorAccentVelvet));
            holder.dayTaskDateMonth.setTextColor(ContextCompat.getColor(context, R.color.colorAccentVelvet));
            holder.totalDayTask.setTextColor(ContextCompat.getColor(context, R.color.colorAccentVelvet));
            holder.cardView.setStrokeColor(ContextCompat.getColor(context, R.color.colorAccentVelvet));

        }else{
            holder.totalDayTaskNotComplete.setVisibility(View.GONE);
            holder.totalDayTaskNotComplete.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            holder.dayTaskLinear_layout.setBackground(context.getResources().getDrawable(R.color.colorPrimary));
            holder.dayTaskDateYear.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            holder.dayTaskDateMonth.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            holder.cardView.setStrokeColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }

    }

    @Override
    public int getItemCount() {
        return dayTask.size();
    }



    public class DayTaskViewHolder extends RecyclerView.ViewHolder{

        MaterialCardView cardView;
        TextView dayTaskDateYear, dayTaskDateMonth, totalDayTask, totalDayTaskEfficiency, totalDayTaskNotComplete;
        LinearLayout dayTaskLinear_layout;

        public DayTaskViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.dayTaskCardView);
            dayTaskDateYear = itemView.findViewById(R.id.dayTaskDateYear);
            dayTaskDateMonth = itemView.findViewById(R.id.dayTaskDateMonth);
            totalDayTask = itemView.findViewById(R.id.totalDayTask);
            totalDayTaskEfficiency = itemView.findViewById(R.id.totalDayTaskEfficiency);
            totalDayTaskNotComplete = itemView.findViewById(R.id.totalDayTaskNotComplete);
            dayTaskLinear_layout = itemView.findViewById(R.id.dayTaskOuterLinear_layout);
            dayTaskLinear_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openDayTaskActivity();
                }
            });


        }


    }

    public void openDayTaskActivity(){
        Intent intent = new Intent(context, OneDayTaskActivity.class);
        context.startActivity(intent);
    }

    private String printSubtractedMin(int subtractedMin){

        if(subtractedMin<0){
            subtractedMin = -1 * subtractedMin;
        }

        if(subtractedMin>=60){
            int hour = subtractedMin/60;
            int min = subtractedMin - (60*hour);

            if(min==0){
                return " " + hour + "小时";
            }else{
                return " " + hour + "小时" + min + "分钟";
            }

        }else{
            return " " + subtractedMin + "分钟";
        }
    }
}
