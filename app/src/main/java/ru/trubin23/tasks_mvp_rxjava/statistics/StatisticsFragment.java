package ru.trubin23.tasks_mvp_rxjava.statistics;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

public class StatisticsFragment extends Fragment implements StatisticsContract.View {

    private StatisticsContract.Presenter mPresenter;

    @NonNull
    public static StatisticsFragment newInstance() {
        return new StatisticsFragment();
    }

    @Override
    public void setPresenter(StatisticsContract.Presenter presenter) {

    }
}
