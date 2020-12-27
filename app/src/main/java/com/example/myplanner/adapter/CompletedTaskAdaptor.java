package com.example.myplanner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myplanner.R;

import java.util.List;

public class CompletedTaskAdaptor extends RecyclerView.Adapter<CompletedTaskAdaptor.CompletedViewHolder> {

    private List<TempCompletedTask> completedTasks;
    Context context;

    public CompletedTaskAdaptor(List<TempCompletedTask> completedTasks, Context context) {
        this.completedTasks = completedTasks;
        this.context = context;
    }

    @NonNull
    @Override
    public CompletedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.completed_row, parent, false);
        return new CompletedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedViewHolder holder, int position) {
        TempCompletedTask completedTask = completedTasks.get(position);
        holder.completedTaskTitle.setText(completedTask.getName());
        holder.completedTaskTime.setText(completedTask.getTime());

        String printStr = printSubtractedMin(completedTask.getSubtractTime_min());

        //양수가 좋은거임 efficiencyMin = 목표시간(9시30분) - 리얼완성시간(9시15분)
        //음수일 경우    efficiencyMin = 목표시간(9시30분) - 리얼완성시간(9시45분)
        if(completedTask.getSubtractTime_min() < 0){
            holder.completedTaskEfficiency.setText("迟到" + printStr);
            holder.completedTaskEfficiency.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }else{
            holder.completedTaskEfficiency.setText("提前" + printStr);
            holder.completedTaskEfficiency.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }
    }

    @Override
    public int getItemCount() {
        return completedTasks.size();
    }

    private String printSubtractedMin(int subtractedMin){

        if(subtractedMin<0){
            subtractedMin = -1 * subtractedMin;
        }

        if(subtractedMin>=60){
            int hour = subtractedMin/60;
            int min = subtractedMin - (60*hour);

            if(min==0){
                return hour + "小时";
            }else{
                return hour + "小时" + min + "分钟";
            }

        }else{
            return subtractedMin + "分钟";
        }
    }

    public class CompletedViewHolder extends RecyclerView.ViewHolder{
        TextView completedTaskTitle, completedTaskTime, completedTaskEfficiency;



        public CompletedViewHolder(@NonNull View itemView) {
            super(itemView);

            completedTaskTitle = itemView.findViewById(R.id.completedTaskTitle);
            completedTaskTime = itemView.findViewById(R.id.completedTaskTime);
            completedTaskEfficiency = itemView.findViewById(R.id.completedTaskEfficiency);

        }
    }

}
