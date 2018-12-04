package ru.trubin23.tasks_mvp_rxjava.tasks.tasklist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.trubin23.tasks_mvp_rxjava.R;
import ru.trubin23.tasks_mvp_rxjava.data.Task;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskHolder> {

    private List<Task> mTasks = new ArrayList<>();
    private TaskItemListener mItemListener;

    public TasksAdapter(TaskItemListener itemListener) {
        mItemListener = itemListener;
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
        return new TaskHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        Task task = mTasks.get(position);
        holder.setTask(task);
        holder.itemView.setOnClickListener(v -> mItemListener.onTaskClick(task.getId()));
        holder.mComplete.setOnClickListener(__ -> {
            if (!holder.mComplete.isChecked()) {
                mItemListener.onActivateTaskClick(task.getId());
            } else {
                mItemListener.onCompleteTaskClick(task.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public void setTasks(@NonNull List<Task> tasks) {
        mTasks.clear();
        mTasks.addAll(tasks);
        this.notifyDataSetChanged();
    }

    class TaskHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private CheckBox mComplete;

        TaskHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.item_title);
            mComplete = itemView.findViewById(R.id.item_completed);
        }

        void setTask(@NonNull Task task) {
            mTitle.setText(task.getTitleForList());
            mComplete.setChecked(task.isCompleted());
        }
    }
}
