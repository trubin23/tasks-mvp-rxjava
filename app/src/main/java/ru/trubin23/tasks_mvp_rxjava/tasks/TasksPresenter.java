package ru.trubin23.tasks_mvp_rxjava.tasks;

import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
import ru.trubin23.tasks_mvp_rxjava.data.source.TasksRepository;
import ru.trubin23.tasks_mvp_rxjava.util.schedulers.BaseSchedulerProvider;

public class TasksPresenter implements TasksContract.Presenter {

    @NonNull
    private TasksRepository mTasksRepository;

    @NonNull
    private TasksContract.View mTasksView;

    @NonNull
    private BaseSchedulerProvider mSchedulerProvider;

    private boolean mFirstLoad = true;

    @NonNull
    private CompositeDisposable mCompositeDisposable;

    TasksPresenter(@NonNull TasksRepository tasksRepository,
                   @NonNull TasksContract.View tasksView,
                   @NonNull BaseSchedulerProvider schedulerProvider) {
        mTasksRepository = tasksRepository;
        mTasksView = tasksView;
        mSchedulerProvider = schedulerProvider;

        mTasksView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadTasks(false);
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void loadTasks(boolean forceUpdate) {
        loadTasks(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    private void loadTasks(boolean forceUpdate, final boolean showLoadingUI) {

    }
}
