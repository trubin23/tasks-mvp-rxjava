package ru.trubin23.tasks_mvp_rxjava.tasks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.trubin23.tasks_mvp_rxjava.Injection;
import ru.trubin23.tasks_mvp_rxjava.R;
import ru.trubin23.tasks_mvp_rxjava.util.ActivityUtils;

public class TasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks_act);

        TasksFragment tasksFragment =
                (TasksFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (tasksFragment == null) {
            tasksFragment = TasksFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), tasksFragment, R.id.content_frame);
        }

        TasksPresenter tasksPresenter = new TasksPresenter(
                Injection.provideTasksRepository(getApplicationContext()),
                tasksFragment,
                Injection.provideSchedulerProvider());
    }
}
