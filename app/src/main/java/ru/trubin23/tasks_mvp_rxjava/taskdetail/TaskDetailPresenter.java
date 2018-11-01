package ru.trubin23.tasks_mvp_rxjava.taskdetail;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Strings;

import io.reactivex.disposables.CompositeDisposable;
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
        if (Strings.isNullOrEmpty(mTaskId)){
            mTaskDetailView.showMissingTask();
            return;
        }
    }

    @Override
    public void editTask() {
        if (Strings.isNullOrEmpty(mTaskId)) {
            if (mTaskDetailView != null) {
                mTaskDetailView.showMissingTask();
            }
            return;
        }
        if (mTaskDetailView != null) {
            mTaskDetailView.showEditTask(mTaskId);
        }
    }

    @Override
    public void deleteTask() {
        if (Strings.isNullOrEmpty(mTaskId)) {
            if (mTaskDetailView != null) {
                mTaskDetailView.showMissingTask();
            }
            return;
        }
        mTasksRepository.deleteTask(mTaskId);
        if (mTaskDetailView != null) {
            mTaskDetailView.showTaskDeleted(mTaskId);
        }
    }
}
