package ru.trubin23.tasks_mvp_rxjava.statistics;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.trubin23.tasks_mvp_rxjava.R;

public class StatisticsFragment extends Fragment implements StatisticsContract.View {

    private StatisticsContract.Presenter mPresenter;

    @NonNull
    public static StatisticsFragment newInstance() {
        return new StatisticsFragment();
    }

    TextView mStatisticsTV;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.statistics_frag, container, false);

        mStatisticsTV = root.findViewById(R.id.statistics);

        return root;
    }

    @Override
    public void setPresenter(StatisticsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }
}
