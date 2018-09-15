package ru.trubin23.tasks_mvp_rxjava.tasks;

import ru.trubin23.tasks_mvp_rxjava.data.source.TasksRepository;

public class TasksPresenter implements TasksContract.Presenter {

    private TasksRepository mTasksRepository;

    private TasksContract.View mTasksView;

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
