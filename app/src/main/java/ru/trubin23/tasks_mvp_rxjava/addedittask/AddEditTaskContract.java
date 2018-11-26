package ru.trubin23.tasks_mvp_rxjava.addedittask;

import android.support.annotation.Nullable;

import ru.trubin23.tasks_mvp_rxjava.BasePresenter;
import ru.trubin23.tasks_mvp_rxjava.BaseView;

public interface AddEditTaskContract {

    interface View extends BaseView<Presenter> {

        void showEmptyTaskError();

        void showTaskList();

        void setTitle(@Nullable String title);

        void setDescription(@Nullable String description);
    }

    interface Presenter extends BasePresenter {

        void saveTask(String title, String description);

        boolean isDataMissing();
    }
}
