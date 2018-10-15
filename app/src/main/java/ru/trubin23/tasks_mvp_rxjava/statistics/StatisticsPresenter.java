package ru.trubin23.tasks_mvp_rxjava.statistics;

import android.support.annotation.NonNull;

import ru.trubin23.tasks_mvp_rxjava.data.source.TasksRepository;
import ru.trubin23.tasks_mvp_rxjava.util.schedulers.BaseSchedulerProvider;

public class StatisticsPresenter implements StatisticsContract.Presenter {

    @NonNull
    private TasksRepository mTasksRepository;

    @NonNull
    private StatisticsContract.View mTasksView;

    @NonNull
    private BaseSchedulerProvider mSchedulerProvider;

    StatisticsPresenter(@NonNull TasksRepository tasksRepository,
                        @NonNull StatisticsContract.View tasksView,
                        @NonNull BaseSchedulerProvider schedulerProvider) {
        mTasksRepository = tasksRepository;
        mTasksView = tasksView;
        mSchedulerProvider = schedulerProvider;

        //mTasksView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
