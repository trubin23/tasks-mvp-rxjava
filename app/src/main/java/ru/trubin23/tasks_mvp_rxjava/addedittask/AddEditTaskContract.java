package ru.trubin23.tasks_mvp_rxjava.addedittask;

import ru.trubin23.tasks_mvp_rxjava.BasePresenter;
import ru.trubin23.tasks_mvp_rxjava.BaseView;

public interface AddEditTaskContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

        void saveTask(String title, String description);
    }
}
