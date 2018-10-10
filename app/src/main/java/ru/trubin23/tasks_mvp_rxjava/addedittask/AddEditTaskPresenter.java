package ru.trubin23.tasks_mvp_rxjava.addedittask;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ru.trubin23.tasks_mvp_rxjava.data.source.TasksRepository;
import ru.trubin23.tasks_mvp_rxjava.util.schedulers.BaseSchedulerProvider;

public class AddEditTaskPresenter implements AddEditTaskContract.Presenter {

    @NonNull
    private TasksRepository mTasksRepository;

    @NonNull
    private AddEditTaskContract.View mAddEditTaskView;

    @NonNull
    private BaseSchedulerProvider mSchedulerProvider;

    @Nullable
    private String mTaskId;

    AddEditTaskPresenter(@Nullable String taskId,
                         @NonNull TasksRepository tasksRepository,
                         @NonNull AddEditTaskContract.View addEditTaskView,
                         @NonNull BaseSchedulerProvider schedulerProvider) {
        mTaskId = taskId;
        mTasksRepository = tasksRepository;
        mAddEditTaskView = addEditTaskView;
        mSchedulerProvider = schedulerProvider;

        mAddEditTaskView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
