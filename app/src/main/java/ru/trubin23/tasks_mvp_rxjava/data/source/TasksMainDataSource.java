package ru.trubin23.tasks_mvp_rxjava.data.source;

public interface TasksMainDataSource extends TasksDataSource {

    void refreshTasks();
}
