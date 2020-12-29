package com.example.myplanner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myplanner.R;
import com.example.myplanner.dataHolder.CompletedRecycleViewData;
import com.example.myplanner.dataHolder.RecycleViewData;
import com.example.myplanner.dataHolder.UncompletedTasksData;

import java.util.ArrayList;
import java.util.List;

public class CompletedTaskAdapter extends RecyclerView.Adapter<CompletedTaskAdapter.CompletedViewHolder> {

    private ArrayList<CompletedRecycleViewData> completedTasks;
    Context context;
//    private OnCompleteItemClickListener mListener;

    public CompletedTaskAdapter(ArrayList<CompletedRecycleViewData> completedTasks, Context context) {
        this.completedTasks = completedTasks;
        this.context = context;
    }

//    public interface OnCompleteItemClickListener {
//
//        void onCancelClick(int task_db_Id);
//
//    }
//
//    public void setOnCompleteItemClickListener(OnCompleteItemClickListener listener) {
//        mListener = listener;
//    }

    


    @NonNull
    @Override
    public CompletedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.completed_row, parent, false);
        return new CompletedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedViewHolder holder, int position) {
        CompletedRecycleViewData completedTask = completedTasks.get(position);
        holder.completedTaskTitle.setText(completedTask.getTask_Name());
        holder.completedTaskTime.setText(completedTask.getTask_Start_Completed());
        holder.completedTaskEfficiency.setText(completedTask.getTask_TimeGap_Msg());

        //양수가 좋은거임 efficiencyMin = 목표시간(9시30분) - 리얼완성시간(9시15분)
        //음수일 경우    efficiencyMin = 목표시간(9시30분) - 리얼완성시간(9시45분)
        if(completedTask.getTimeGap() < 0){
            holder.completedTaskEfficiency.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }else{
            holder.completedTaskEfficiency.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }
    }

    @Override
    public int getItemCount() {
        return completedTasks.size();
    }

    public class CompletedViewHolder extends RecyclerView.ViewHolder{
        TextView completedTaskTitle, completedTaskTime, completedTaskEfficiency;
//        ImageView task_not_complete_icon;



        public CompletedViewHolder(@NonNull View itemView) {
            super(itemView);

            completedTaskTitle = itemView.findViewById(R.id.completedTaskTitle);
            completedTaskTime = itemView.findViewById(R.id.completedTaskTime);
            completedTaskEfficiency = itemView.findViewById(R.id.completedTaskEfficiency);
//            task_not_complete_icon = itemView.findViewById(R.id.task_not_complete_icon);
//            task_not_complete_icon.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View view) {
//                    if (listener != null) {
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION) {
//
//                            CompletedRecycleViewData completedRecycleViewData = completedTasks.get(position);
//
//                            listener.onCancelClick(completedRecycleViewData.getTask_db_Id());
//
//                        }
//                    }
//                }
//            });

        }
    }

}
