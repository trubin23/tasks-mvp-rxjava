package ru.trubin23.tasks_mvp_rxjava.taskdetail;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ru.trubin23.tasks_mvp_rxjava.data.source.TasksRepository;
import ru.trubin23.tasks_mvp_rxjava.util.schedulers.BaseSchedulerProvider;

public class TaskDetailPresenter implements TaskDetailContract.Presenter {

    @NonNull
    private TasksRepository mTasksRepository;

    @NonNull
    private TaskDetailContract.View mTaskDetailView;

    @NonNull
    private BaseSchedulerProvider mSchedulerProvider;

    @Nullable
    private String mTaskId;

    TaskDetailPresenter(@Nullable String taskId,
                        @NonNull TasksRepository tasksRepository,
                        @NonNull TaskDetailContract.View taskDetailView,
                        @NonNull BaseSchedulerProvider schedulerProvider) {
        mTaskId = taskId;
        mTasksRepository = tasksRepository;
        mTaskDetailView = taskDetailView;
        mSchedulerProvider = schedulerProvider;

        mTaskDetailView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
