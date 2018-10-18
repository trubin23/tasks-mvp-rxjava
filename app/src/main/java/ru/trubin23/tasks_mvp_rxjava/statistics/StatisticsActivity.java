package ru.trubin23.tasks_mvp_rxjava.statistics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.trubin23.tasks_mvp_rxjava.Injection;
import ru.trubin23.tasks_mvp_rxjava.R;
import ru.trubin23.tasks_mvp_rxjava.util.ActivityUtils;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_act);

        StatisticsFragment statisticsFragment =
                (StatisticsFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (statisticsFragment == null) {
            statisticsFragment = new StatisticsFragment();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), statisticsFragment, R.id.content_frame);
        }

        StatisticsPresenter statisticsPresenter = new StatisticsPresenter(
                Injection.provideTasksRepository(getApplicationContext()),
                statisticsFragment,
                Injection.provideSchedulerProvider());
    }
}
