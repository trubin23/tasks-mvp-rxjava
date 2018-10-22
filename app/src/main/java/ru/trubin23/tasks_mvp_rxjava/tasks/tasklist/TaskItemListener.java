package ru.trubin23.tasks_mvp_rxjava.tasks.tasklist;

public interface TaskItemListener {

    void onTaskClick(String taskId);

    void onCompleteTaskClick(String taskId);

    void onActivateTaskClick(String taskId);
}
