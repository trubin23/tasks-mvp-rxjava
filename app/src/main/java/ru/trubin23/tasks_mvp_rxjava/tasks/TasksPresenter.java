package ru.trubin23.tasks_mvp_rxjava.tasks;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ru.trubin23.tasks_mvp_rxjava.data.Task;
import ru.trubin23.tasks_mvp_rxjava.data.source.TasksRepository;
import ru.trubin23.tasks_mvp_rxjava.util.schedulers.BaseSchedulerProvider;

import static ru.trubin23.tasks_mvp_rxjava.tasks.TasksFilterType.ALL_TASKS;

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

    @NonNull
    private TasksFilterType mFilterType = ALL_TASKS;

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
        if (showLoadingUI) {
            mTasksView.setLoadingIndicator(true);
        }
        if (forceUpdate) {
            mTasksRepository.refreshTasks();
        }

        mCompositeDisposable.clear();
        Disposable disposable = mTasksRepository
                .getTasks()
                .flatMap(Flowable::fromIterable)
                .filter(task -> {
                    switch (mFilterType) {
                        case ACTIVE_TASKS:
                            return task.isActive();
                        case COMPLETED_TASKS:
                            return task.isCompleted();
                        case ALL_TASKS:
                        default:
                            return true;
                    }
                })
                .toList()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(tasks -> {
                            processTasks(tasks);
                            mTasksView.setLoadingIndicator(false);
                        },
                        throwable -> mTasksView.showLoadingTasksError());

    }

    private void processTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            processEmptyTasks();
        } else {
            mTasksView.showTasks(tasks);
            showFilterLabel();
        }
    }

    private void processEmptyTasks() {
        switch (mFilterType) {
            case ACTIVE_TASKS:
                mTasksView.showNoActiveTasks();
                break;
            case COMPLETED_TASKS:
                mTasksView.showNoCompletedTasks();
                break;
            case ALL_TASKS:
                mTasksView.showNoTasks();
                break;
        }
    }

    private void showFilterLabel() {
        switch (mFilterType) {
            case ACTIVE_TASKS:
                mTasksView.showActiveFilterLabel();
                break;
            case COMPLETED_TASKS:
                mTasksView.showCompletedFilterLabel();
                break;
            case ALL_TASKS:
                mTasksView.showAllFilterLabel();
                break;
        }
    }

    @Override
    public void setFiltering(TasksFilterType filterType) {
        mFilterType = filterType;
    }

    @Override
    public TasksFilterType getFiltering() {
        return null;
    }
}
