package ru.trubin23.tasks_mvp_rxjava.addedittask;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.disposables.CompositeDisposable;
import ru.trubin23.tasks_mvp_rxjava.data.Task;
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

    @NonNull
    private CompositeDisposable mCompositeDisposable;

    private boolean mIsDataMissing;

    AddEditTaskPresenter(@Nullable String taskId,
                         @NonNull TasksRepository tasksRepository,
                         @NonNull AddEditTaskContract.View addEditTaskView,
                         @NonNull BaseSchedulerProvider schedulerProvider) {
        mTaskId = taskId;
        mTasksRepository = tasksRepository;
        mAddEditTaskView = addEditTaskView;
        mSchedulerProvider = schedulerProvider;

        mCompositeDisposable = new CompositeDisposable();
        mAddEditTaskView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        if (!isNewTask() && mIsDataMissing){
            populateTask();
        }
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    private void populateTask() {
        mCompositeDisposable.add(mTasksRepository
                .getTask(mTaskId)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(
                        taskOptional -> {
                            if (taskOptional.isPresent()){
                                Task task = taskOptional.get();

                                mAddEditTaskView.setTitle(task.getTitle());
                                mAddEditTaskView.setDescription(task.getDescription());

                                mIsDataMissing = false;
                            } else {
                                mAddEditTaskView.showEmptyTaskError();
                            }
                        },
                        throwable -> mAddEditTaskView.showEmptyTaskError()
                ));
    }

    private boolean isNewTask() {
        return mTaskId == null;
    }

    @Override
    public void saveTask(String title, String description) {
        Task task;
        if (isNewTask()) {
            task = new Task(title, description);
        } else {
            task = new Task(title, description, mTaskId);
        }

        if (task.isEmpty()) {
            mAddEditTaskView.showEmptyTaskError();
        } else {
            mTasksRepository.saveTask(task);
            mAddEditTaskView.showTaskList();
        }
    }

    @Override
    public boolean isDataMissing(){
        return mIsDataMissing;
    }
}
