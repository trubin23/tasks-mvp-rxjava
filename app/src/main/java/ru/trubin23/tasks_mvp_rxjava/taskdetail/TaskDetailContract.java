package ru.trubin23.tasks_mvp_rxjava.taskdetail;

import ru.trubin23.tasks_mvp_rxjava.BasePresenter;
import ru.trubin23.tasks_mvp_rxjava.BaseView;

public interface TaskDetailContract {

    interface View extends BaseView<Presenter> {

        void showMissingTask();
    }

    interface Presenter extends BasePresenter {

    }
}
