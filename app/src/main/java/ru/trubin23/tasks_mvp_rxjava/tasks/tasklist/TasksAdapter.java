package ru.trubin23.tasks_mvp_rxjava.tasks.tasklist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.trubin23.tasks_mvp_rxjava.R;
import ru.trubin23.tasks_mvp_rxjava.data.Task;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskHolder> {

    private List<Task> mTasks;
    private TaskItemListener mItemListener;

    public TasksAdapter(List<Task> tasks, TaskItemListener itemListener) {
        mItemListener = itemListener;
        setTasks(tasks);
    }

    @NonNull
    @Override
    public TasksAdapter.TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View itemView = LayoutInflater.from(parent.getContext())
        //        .inflate(R.layout.task_item, parent, false);
        //return new TaskHolder(itemView);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TasksAdapter.TaskHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public void setTasks(@NonNull List<Task> tasks) {
        mTasks = tasks;
        notifyDataSetChanged();
    }

    class TaskHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;

        public TaskHolder(View itemView) {
            super(itemView);
        }

        void setTask(@NonNull Task task) {
        }
    }
}
