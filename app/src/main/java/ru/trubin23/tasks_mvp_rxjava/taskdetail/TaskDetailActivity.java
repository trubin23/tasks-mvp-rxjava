package ru.trubin23.tasks_mvp_rxjava.taskdetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.trubin23.tasks_mvp_rxjava.Injection;
import ru.trubin23.tasks_mvp_rxjava.R;
import ru.trubin23.tasks_mvp_rxjava.util.ActivityUtils;

public class TaskDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_detail_act);

        TaskDetailFragment taskDetailFragment = (TaskDetailFragment)
                getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (taskDetailFragment == null) {
            taskDetailFragment = new TaskDetailFragment();
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
