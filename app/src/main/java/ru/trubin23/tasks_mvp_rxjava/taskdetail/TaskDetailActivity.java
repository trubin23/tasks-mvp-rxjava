package ru.trubin23.tasks_mvp_rxjava.taskdetail;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ru.trubin23.tasks_mvp_rxjava.Injection;
import ru.trubin23.tasks_mvp_rxjava.R;
import ru.trubin23.tasks_mvp_rxjava.util.ActivityUtils;

public class TaskDetailActivity extends AppCompatActivity {

    public static final String SHOW_TASK_ID = "TASK_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_detail_act);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        TaskDetailFragment taskDetailFragment = (TaskDetailFragment)
                getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (taskDetailFragment == null) {
            taskDetailFragment = TaskDetailFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), taskDetailFragment, R.id.content_frame);
        }

        TaskDetailPresenter taskDetailPresenter = new TaskDetailPresenter(
                null,
                Injection.provideTasksRepository(getApplicationContext()),
                taskDetailFragment,
                Injection.provideSchedulerProvider());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
