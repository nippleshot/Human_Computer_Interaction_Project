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

import com.example.myplanner.FixTaskActivity;
import com.example.myplanner.R;
import com.example.myplanner.Task;
import com.example.myplanner.dataHelper.Converter;
import com.example.myplanner.dataHelper.MessageHelper;
import com.example.myplanner.dataHolder.CompletedTasksData;
import com.example.myplanner.dataHolder.RecycleViewData;
import com.example.myplanner.dataHolder.UncompletedTasksData;
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
    private ArrayList<RecycleViewData> dataItems;
    private Context context;
    private OnItemClickListener mListener;
    public CompletedTaskAdapter completedTaskAdapter;

    public TaskListAdapter(ArrayList<Task> tasks, Context context) {
        this.dataItems = Converter.toRecycleViewDataList( tasks );
        this.context = context;
    }

    public interface OnItemClickListener {
        void onDeleteClick(int task_db_Id);

        void onInsertClick(int task_db_Id);

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
            UncompletedTasksData task = dataItems.get(position).getCheckListViewData();

            ((TaskViewHolder) holder).taskTitle.setText(task.getTask_Name());
            ((TaskViewHolder) holder).taskTimeLeft.setText(task.getTask_TimeMsg());
            ((TaskViewHolder) holder).taskPlace.setText(task.getTask_Place());
            ((TaskViewHolder) holder).taskMemo.setText(task.getTask_Memo());


            boolean isExpandable = task.isExpandable();
            ((TaskViewHolder) holder).expandableLayout.setVisibility(isExpandable? View.VISIBLE : View.GONE);

        }
        else if(holder instanceof HeadViewHolder){
            CompletedTasksData head = dataItems.get(position).getAnalysisViewData();

            ((HeadViewHolder) holder).taskDate.setText(head.getTask_Date());
            ((HeadViewHolder) holder).taskTotal.setText("总数  "+head.getTask_Total());
            ((HeadViewHolder) holder).taskCompletedNum.setText("完成  "+head.getTask_CompletedNum());
            ((HeadViewHolder) holder).taskCompletedInTimeNum.setText("(时间内完成  "+head.getTask_CompletedInTimeNum()+")");
            ((HeadViewHolder) holder).taskNotCompletedNum.setText("未完  "+head.getTask_UncompletedNum());

            int totalEfficiency = head.getTask_TotalEfficiency();
            ((HeadViewHolder) holder).totalTaskEfficiency.setText( MessageHelper.changeToHour_Min(totalEfficiency, true) );

            //양수가 좋은거임 efficiencyMin = 목표시간(9시30분) - 리얼완성시간(9시15분)
            //음수일 경우    efficiencyMin = 목표시간(9시30분) - 리얼완성시간(9시45분)
            if(totalEfficiency<0){
                ((HeadViewHolder) holder).totalTaskEfficiency.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                ((HeadViewHolder) holder).totalTaskEfficiency.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_trending_down_s, 0, 0, 0);
            }else{
                ((HeadViewHolder) holder).totalTaskEfficiency.setTextColor(context.getResources().getColor(R.color.colorAccent));
                ((HeadViewHolder) holder).totalTaskEfficiency.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_trending_up_s, 0, 0, 0);
            }


            setPieChart( ((HeadViewHolder) holder).taskPieChart, head.getTask_CompletedInTimeNum() ,head.getTask_CompletedNum() - head.getTask_CompletedInTimeNum());

            LinearLayoutManager layoutManager = new LinearLayoutManager(
                    ((HeadViewHolder) holder).completedRecyclerView.getContext(),
                    LinearLayoutManager.VERTICAL,
                    false
            );
            layoutManager.setInitialPrefetchItemCount(head.getCompletedRecycleViewDataList().size());
            completedTaskAdapter = new CompletedTaskAdapter(head.getCompletedRecycleViewDataList(), context);

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
            dividerItemDecoration.setDrawable(context.getResources().getDrawable(R.drawable.completed_recycleview_divider));

            ((HeadViewHolder) holder).completedRecyclerView.setLayoutManager(layoutManager);
            ((HeadViewHolder) holder).completedRecyclerView.addItemDecoration(dividerItemDecoration);
            ((HeadViewHolder) holder).completedRecyclerView.setAdapter(completedTaskAdapter);
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
                    CompletedTasksData tempHead = dataItems.get(getAdapterPosition()).getAnalysisViewData();
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
                    UncompletedTasksData tempTask = dataItems.get(getAdapterPosition()).getCheckListViewData();
                    tempTask.setExpandable(!tempTask.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            fixButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    RecycleViewData recycleViewData = dataItems.get(position);
                    UncompletedTasksData to_FixTask = recycleViewData.getCheckListViewData();
                    openFixTaskActivity( to_FixTask.getTask_db_index() );
                }
            });

            taskDelete_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {

                            RecycleViewData recycleViewData = dataItems.get(position);
                            UncompletedTasksData to_RemoveTask = recycleViewData.getCheckListViewData();
                            listener.onDeleteClick(to_RemoveTask.getTask_db_index());

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

                            RecycleViewData recycleViewData = dataItems.get(position);
                            UncompletedTasksData to_CompleteTask = recycleViewData.getCheckListViewData();
                            listener.onInsertClick(to_CompleteTask.getTask_db_index());

                        }
                    }
                }
            });


        }
    }

    public void openFixTaskActivity(int task_db_Id){
        Intent intent = new Intent(context, FixTaskActivity.class);
        intent.putExtra("task_db_Id", task_db_Id);
        context.startActivity(intent);
    }


}
