package ru.trubin23.tasks_mvp_rxjava.tasks;

import java.util.List;

import ru.trubin23.tasks_mvp_rxjava.BasePresenter;
import ru.trubin23.tasks_mvp_rxjava.BaseView;
import ru.trubin23.tasks_mvp_rxjava.data.Task;

public interface TasksContract {

    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);

        void showLoadingTasksError();

        void showTasks(List<Task> tasks);

        void showActiveFilterLabel();

        void showCompletedFilterLabel();

        void showAllFilterLabel();

        void showNoActiveTasks();

        void showNoCompletedTasks();

        void showNoTasks();

        void showCompletedTasksCleared();
    }

    interface Presenter extends BasePresenter {
        void loadTasks(boolean forceUpdate);

        void setFiltering(TasksFilterType filterType);

        TasksFilterType getFiltering();

        void clearCompletedTasks();
    }
}
