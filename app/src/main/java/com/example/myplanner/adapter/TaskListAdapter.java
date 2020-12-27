package com.example.myplanner.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myplanner.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEAD_VIEW_HOLDER = 1;
    private static final int TASK_VIEW_HOLDER = 2;

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<DataItem> dataItems;
    private Context context;
    private OnItemClickListener mListener;
    CompletedTaskAdaptor completedTaskAdaptor;

    public TaskListAdapter(List<DataItem> dataItems, Context context) {
        this.dataItems = dataItems;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onDeleteClick(int position);

        void onInsertClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == TASK_VIEW_HOLDER){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row, parent, false);
            return new TaskViewHolder(view, mListener);

        }else{
            // viewType == HEAD_VIEW_HOLDER
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row_head, parent, false);
            return new HeadViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof TaskViewHolder){
            TempTask task = dataItems.get(position).getTaskInfo();

            ((TaskViewHolder) holder).taskTitle.setText(task.getTaskTitle());

            int minLeft = task.getTaskTimeLeft();

            if(minLeft>=60){
                int hours = minLeft/60;
                if(minLeft%60==0){
                    ((TaskViewHolder) holder).taskTimeLeft.setText("开始前"+hours+"小时");

                }else{
                    int mins = minLeft - (hours*60);
                    ((TaskViewHolder) holder).taskTimeLeft.setText("开始前"+hours+"小时"+mins+"分钟");

                }
            }else{
                ((TaskViewHolder) holder).taskTimeLeft.setText("开始前"+minLeft+"分钟");
            }

            ((TaskViewHolder) holder).taskPlace.setText(task.getTaskPlace());
            ((TaskViewHolder) holder).taskMemo.setText(task.getTaskMemo());


            boolean isExpandable = task.isExpandable();
            ((TaskViewHolder) holder).expandableLayout.setVisibility(isExpandable? View.VISIBLE : View.GONE);

        }
        else if(holder instanceof HeadViewHolder){
            TempHead head = dataItems.get(position).getHeadInfo();

            ((HeadViewHolder) holder).taskDate.setText(head.getTaskDate());
            ((HeadViewHolder) holder).taskTotal.setText("总数  "+head.getTaskTotal());
            ((HeadViewHolder) holder).taskCompletedNum.setText("完成  "+head.getTaskCompletedNum());
            ((HeadViewHolder) holder).taskCompletedInTimeNum.setText("(时间内完成  "+head.getTaskCompletedInTimeNum()+")");
            ((HeadViewHolder) holder).taskNotCompletedNum.setText("未完  "+head.getTaskNotCompletedNum());

            int totalEfficiency = countTotalEfficiency(head.getCompletedTasks());
            ((HeadViewHolder) holder).totalTaskEfficiency.setText(printSubtractedMin(totalEfficiency));

            //양수가 좋은거임 efficiencyMin = 목표시간(9시30분) - 리얼완성시간(9시15분)
            //음수일 경우    efficiencyMin = 목표시간(9시30분) - 리얼완성시간(9시45분)
            if(totalEfficiency<0){
                ((HeadViewHolder) holder).totalTaskEfficiency.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                ((HeadViewHolder) holder).totalTaskEfficiency.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_trending_down_s, 0, 0, 0);
            }else{
                ((HeadViewHolder) holder).totalTaskEfficiency.setTextColor(context.getResources().getColor(R.color.colorAccent));
                ((HeadViewHolder) holder).totalTaskEfficiency.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_trending_up_s, 0, 0, 0);
            }


            setPieChart( ((HeadViewHolder) holder).taskPieChart, head.getTaskCompletedInTimeNum() ,head.getTaskCompletedNum() - head.getTaskCompletedInTimeNum());

            LinearLayoutManager layoutManager = new LinearLayoutManager(
                    ((HeadViewHolder) holder).completedRecyclerView.getContext(),
                    LinearLayoutManager.VERTICAL,
                    false
            );
            layoutManager.setInitialPrefetchItemCount(head.getCompletedTasks().size());
            completedTaskAdaptor = new CompletedTaskAdaptor(head.getCompletedTasks(), context);

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
            dividerItemDecoration.setDrawable(context.getResources().getDrawable(R.drawable.completed_recycleview_divider));

            ((HeadViewHolder) holder).completedRecyclerView.setLayoutManager(layoutManager);
            ((HeadViewHolder) holder).completedRecyclerView.addItemDecoration(dividerItemDecoration);
            ((HeadViewHolder) holder).completedRecyclerView.setAdapter(completedTaskAdaptor);
            ((HeadViewHolder) holder).completedRecyclerView.setRecycledViewPool(viewPool);

            boolean isExpandable = head.isExpandable();
            ((HeadViewHolder) holder).expandableCompletedTaskLayout.setVisibility(isExpandable? View.VISIBLE : View.GONE);
            ((HeadViewHolder) holder).expandable_icon.setVisibility(isExpandable? View.GONE : View.VISIBLE);



        }
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return dataItems.get(position).getViewType();
    }

    private int countTotalEfficiency(List<TempCompletedTask> CompletedTasks){
        int total = 0;
        for(int i=0; i<CompletedTasks.size(); i++){
            total = total + CompletedTasks.get(i).getSubtractTime_min();
        }

        return total;
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

    private void setPieChart(PieChart pieChart, int completedInTime, int completedOverTime){
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.99f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(31f);

        ArrayList<PieEntry> yValues = new ArrayList<>();

        yValues.add(new PieEntry(completedInTime, "时间内完成"));
        yValues.add(new PieEntry(completedOverTime, "过时完成"));

        pieChart.animateY(1000, Easing.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(context.getResources().getColor(R.color.colorAccent), context.getResources().getColor(R.color.colorPrimary));

        PieData data = new PieData(dataSet);
        data.setValueTextSize(20f);
        data.setValueTextColor(Color.BLACK);


        pieChart.getLegend().setFormSize(16f);
        pieChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        pieChart.getLegend().setTextSize(13f);
        pieChart.setData(data);
        pieChart.setDrawSliceText(false);
    }

    public class HeadViewHolder extends RecyclerView.ViewHolder{

        TextView taskDate, taskTotal, taskCompletedNum, taskCompletedInTimeNum, taskNotCompletedNum, totalTaskEfficiency;
        PieChart taskPieChart;
        LinearLayout completedLinearLayout;
        ImageView expandable_icon;
        RelativeLayout expandableCompletedTaskLayout;
        RecyclerView completedRecyclerView;

        public HeadViewHolder(@NonNull View itemView) {
            super(itemView);

            taskDate = itemView.findViewById(R.id.taskDate);
            taskTotal = itemView.findViewById(R.id.taskTotal);
            taskCompletedNum = itemView.findViewById(R.id.taskCompletedNum);
            taskCompletedInTimeNum = itemView.findViewById(R.id.taskCompletedInTimeNum);
            taskNotCompletedNum = itemView.findViewById(R.id.taskNotCompletedNum);
            totalTaskEfficiency = itemView.findViewById(R.id.totalTaskEfficiency);
            taskPieChart = itemView.findViewById(R.id.taskPieChart);
            completedLinearLayout = itemView.findViewById(R.id.completedLinear_layout);
            expandable_icon = itemView.findViewById(R.id.expandable_icon);
            expandableCompletedTaskLayout = itemView.findViewById(R.id.expandable_completedTask_layout);
            completedRecyclerView = itemView.findViewById(R.id.completedRecyclerView);

            completedLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TempHead tempHead = dataItems.get(getAdapterPosition()).getHeadInfo();
                    tempHead.setExpandable(!tempHead.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });

        }
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder{
        TextView taskTitle, taskTimeLeft, taskPlace, taskMemo;
        LinearLayout linearLayout;
        RelativeLayout expandableLayout;
        MaterialButton fixButton;
        ImageView taskDelete_icon;
        MaterialButton taskCompleted;



        public TaskViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            taskTitle = itemView.findViewById(R.id.taskTitle);
            taskTimeLeft = itemView.findViewById(R.id.taskTimeLeft);
            taskPlace = itemView.findViewById(R.id.taskPlace);
            taskMemo = itemView.findViewById(R.id.taskMemo);
            linearLayout = itemView.findViewById(R.id.linear_layout);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            fixButton = itemView.findViewById(R.id.taskFixButton);
            taskDelete_icon = itemView.findViewById(R.id.taskDelete_icon);
            taskCompleted = itemView.findViewById(R.id.taskCompleted);


            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TempTask tempTask = dataItems.get(getAdapterPosition()).getTaskInfo();
                    tempTask.setExpandable(!tempTask.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            fixButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openFixTaskActivity();
                }
            });

            taskDelete_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

            taskCompleted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {

                            listener.onInsertClick(position);
                        }
                    }
                }
            });


        }
    }

    public void openFixTaskActivity(){
        Intent intent = new Intent(context, FixTaskActivity.class);
        context.startActivity(intent);
    }


}
