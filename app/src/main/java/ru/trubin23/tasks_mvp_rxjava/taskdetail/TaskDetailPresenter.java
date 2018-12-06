package ru.trubin23.tasks_mvp_rxjava.taskdetail;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.common.base.Optional;
import com.google.common.base.Strings;

import io.reactivex.disposables.CompositeDisposable;
import ru.trubin23.tasks_mvp_rxjava.data.Task;
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

    @NonNull
    private CompositeDisposable mCompositeDisposable;

    TaskDetailPresenter(@Nullable String taskId,
                        @NonNull TasksRepository tasksRepository,
                        @NonNull TaskDetailContract.View taskDetailView,
                        @NonNull BaseSchedulerProvider schedulerProvider) {
        mTaskId = taskId;
        mTasksRepository = tasksRepository;
        mTaskDetailView = taskDetailView;
        mSchedulerProvider = schedulerProvider;

        mTaskDetailView.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {
        openTask();
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    private void openTask() {
        if (Strings.isNullOrEmpty(mTaskId)) {
            mTaskDetailView.showMissingTask();
            return;
        }

        mTaskDetailView.setLoadingIndicator(true);
        mCompositeDisposable.add(mTasksRepository
                .getTask(mTaskId)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(
                        this::showTask,
                        throwable -> {
                            Log.d("show_task", "throwable");
                        }
                ));
    }

    private void showTask(Task task) {
        Log.d("show_task", "private void showTask(Task task)");
        String title = task.getTitle();
        String description = task.getDescription();

        if (Strings.isNullOrEmpty(title)){
            mTaskDetailView.hideTitle();
        } else {
            mTaskDetailView.showTitle(title);
        }

        if (Strings.isNullOrEmpty(description)){
            mTaskDetailView.hideDescription();
        } else {
            mTaskDetailView.showDescription(description);
        }

        mTaskDetailView.showCompletionStatus(task.isCompleted());
    }

    @Override
    public void editTask() {
        if (Strings.isNullOrEmpty(mTaskId)) {
            mTaskDetailView.showMissingTask();
        } else {
            mTaskDetailView.showEditTask(mTaskId);
        }
    }

    @Override
    public void deleteTask() {
        if (Strings.isNullOrEmpty(mTaskId)) {
            mTaskDetailView.showMissingTask();
        } else {
            mTasksRepository.deleteTask(mTaskId);
            mTaskDetailView.showTaskDeleted(mTaskId);
        }
    }

    @Override
    public void completeTask() {
        if (Strings.isNullOrEmpty(mTaskId)) {
            mTaskDetailView.showMissingTask();
        } else {
            mTasksRepository.completeTask(mTaskId);
            mTaskDetailView.showTaskMarkedComplete();
        }
    }

    @Override
    public void activateTask() {
        if (Strings.isNullOrEmpty(mTaskId)) {
            mTaskDetailView.showMissingTask();
        } else {
            mTasksRepository.activateTask(mTaskId);
            mTaskDetailView.showTaskMarkedActive();
        }
    }
}
