package ru.trubin23.tasks_mvp_rxjava.addedittask;

import android.support.annotation.NonNull;

import ru.trubin23.tasks_mvp_rxjava.BasePresenter;
import ru.trubin23.tasks_mvp_rxjava.BaseView;

public interface AddEditTaskContract {

    interface View extends BaseView<Presenter> {

        void setTitle(@NonNull String title);

        void setDescription(@NonNull String description);
    }

    interface Presenter extends BasePresenter {

        void saveTask(String title, String description);
    }
}
