package ru.trubin23.tasks_mvp_rxjava.tasks;

import android.support.annotation.NonNull;

import java.util.List;

import ru.trubin23.tasks_mvp_rxjava.BasePresenter;
import ru.trubin23.tasks_mvp_rxjava.BaseView;
import ru.trubin23.tasks_mvp_rxjava.data.Task;

public interface TasksContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showLoadingTasksError();

        void showTasks(@NonNull List<Task> tasks);

        void showActiveFilterLabel();

        void showCompletedFilterLabel();

        void showAllFilterLabel();

        void showNoActiveTasks();

        void showNoCompletedTasks();

        void showNoTasks();

        void showCompletedTasksCleared();

        void showTaskMarkedComplete();

        void showTaskMarkedActive();

        void showTaskDetail(@NonNull String taskId);

        void showAddTask();

        void showSuccessfullySavedMessage();
    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadTasks(boolean forceUpdate);

        void setFiltering(TasksFilterType filterType);

        void clearCompletedTask();

        void addNewTask();

        void changeCompletedTask(@NonNull String taskId, boolean completed);

        TasksFilterType getFiltering();

        void openTaskDetails(@NonNull String taskId);

        void refreshTasks();
    }
}
