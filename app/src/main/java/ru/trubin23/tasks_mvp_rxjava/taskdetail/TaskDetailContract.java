package ru.trubin23.tasks_mvp_rxjava.taskdetail;

import android.support.annotation.NonNull;

import ru.trubin23.tasks_mvp_rxjava.BasePresenter;
import ru.trubin23.tasks_mvp_rxjava.BaseView;

public interface TaskDetailContract {

    interface View extends BaseView<Presenter> {

        void showMissingTask();

        void showCompletionStatus(boolean complete);

        void showEditTask(String taskId);

        void showTaskDeleted(String taskId);

        void showTaskMarkedComplete();

        void showTaskMarkedActive();

        void setTitle(@NonNull String title);

        void setDescription(@NonNull String description);

        void setLoadingIndicator(boolean active);
    }

    interface Presenter extends BasePresenter {

        void editTask();

        void deleteTask();
    }
}
