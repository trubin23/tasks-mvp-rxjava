package ru.trubin23.tasks_mvp_rxjava.statistics;

import android.support.annotation.NonNull;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import ru.trubin23.tasks_mvp_rxjava.data.Task;
import ru.trubin23.tasks_mvp_rxjava.data.source.TasksRepository;
import ru.trubin23.tasks_mvp_rxjava.util.schedulers.BaseSchedulerProvider;

public class StatisticsPresenter implements StatisticsContract.Presenter {

    @NonNull
    private TasksRepository mTasksRepository;

    @NonNull
    private StatisticsContract.View mStatisticsView;

    @NonNull
    private BaseSchedulerProvider mSchedulerProvider;

    @NonNull
    private CompositeDisposable mCompositeDisposable;

    StatisticsPresenter(@NonNull TasksRepository tasksRepository,
                        @NonNull StatisticsContract.View statisticsView,
                        @NonNull BaseSchedulerProvider schedulerProvider) {
        mTasksRepository = tasksRepository;
        mStatisticsView = statisticsView;
        mSchedulerProvider = schedulerProvider;

        mCompositeDisposable = new CompositeDisposable();
        mStatisticsView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        mStatisticsView.setProgressIndicator(true);

        Flowable<Task> tasks = mTasksRepository
                .getTasks()
                .flatMap(Flowable::fromIterable);
        Flowable<Long> completedTasks = tasks.filter(Task::isCompleted).count().toFlowable();
        Flowable<Long> activeTasks = tasks.filter(Task::isActive).count().toFlowable();
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }
}
