package ru.trubin23.tasks_mvp_rxjava.tasks;

import ru.trubin23.tasks_mvp_rxjava.BasePresenter;
import ru.trubin23.tasks_mvp_rxjava.BaseView;

public interface TasksContract {

    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);

        void showLoadingTasksError();
    }

    interface Presenter extends BasePresenter {
        void loadTasks(boolean forceUpdate);
    }
}
