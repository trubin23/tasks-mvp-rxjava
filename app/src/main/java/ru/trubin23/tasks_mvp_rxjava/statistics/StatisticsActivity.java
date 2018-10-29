package ru.trubin23.tasks_mvp_rxjava.statistics;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ru.trubin23.tasks_mvp_rxjava.Injection;
import ru.trubin23.tasks_mvp_rxjava.R;
import ru.trubin23.tasks_mvp_rxjava.util.ActivityUtils;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_act);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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
