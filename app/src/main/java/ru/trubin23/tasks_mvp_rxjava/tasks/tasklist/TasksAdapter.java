package ru.trubin23.tasks_mvp_rxjava.tasks.tasklist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.trubin23.tasks_mvp_rxjava.data.Task;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskHolder> {

    private List<Task> mTasks;
    private TaskItemListener mItemListener;

    public TasksAdapter(List<Task> tasks, TaskItemListener itemListener) {
        //TODO: set tasks
        mItemListener = itemListener;
    }


    @NonNull
    @Override
    public TasksAdapter.TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TasksAdapter.TaskHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class TaskHolder extends RecyclerView.ViewHolder {

        public TaskHolder(View itemView) {
            super(itemView);
        }

        void setTask(@NonNull Task task) {
        }
    }
}
