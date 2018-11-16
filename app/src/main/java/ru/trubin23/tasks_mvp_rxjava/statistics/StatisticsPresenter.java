package ru.trubin23.tasks_mvp_rxjava.statistics;

import android.support.annotation.NonNull;
import android.util.Pair;

import com.google.common.primitives.Ints;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
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

        Disposable disposable = Flowable
                .zip(completedTasks, activeTasks, (completed, active) -> Pair.create(active, completed))
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .doFinally(() -> {})
                .subscribe(
                        stats -> mStatisticsView.showStatistics(Ints.saturatedCast(stats.first), Ints.saturatedCast(stats.first)),
                        throwable ->mStatisticsView.showLoadingStatisticsError(),
                        () -> mStatisticsView.setProgressIndicator(false)
                );
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }
}
