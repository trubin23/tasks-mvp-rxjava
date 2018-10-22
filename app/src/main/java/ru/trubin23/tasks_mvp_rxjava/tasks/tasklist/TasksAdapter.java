package ru.trubin23.tasks_mvp_rxjava.tasks.tasklist;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import ru.trubin23.tasks_mvp_rxjava.data.Task;

public class TasksAdapter extends BaseAdapter {

    private List<Task> mTasks;
    private TaskItemListener mItemListener;

    public TasksAdapter(List<Task> tasks, TaskItemListener itemListener){
        //TODO: set tasks
        mItemListener = itemListener;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
