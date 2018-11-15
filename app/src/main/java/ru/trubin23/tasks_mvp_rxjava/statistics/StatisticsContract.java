package ru.trubin23.tasks_mvp_rxjava.statistics;

import ru.trubin23.tasks_mvp_rxjava.BasePresenter;
import ru.trubin23.tasks_mvp_rxjava.BaseView;

public interface StatisticsContract {

    interface View extends BaseView<Presenter> {

        void setProgressIndicator(boolean active);
    }

    interface Presenter extends BasePresenter {

    }
}
