package ru.trubin23.tasks_mvp_rxjava.addedittask;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.trubin23.tasks_mvp_rxjava.Injection;
import ru.trubin23.tasks_mvp_rxjava.R;
import ru.trubin23.tasks_mvp_rxjava.util.ActivityUtils;

public class AddEditTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_task_act);

        AddEditTaskFragment addEditTaskFragment = (AddEditTaskFragment)
                getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (addEditTaskFragment == null) {
            addEditTaskFragment = new AddEditTaskFragment();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), addEditTaskFragment, R.id.content_frame);
        }

        AddEditTaskPresenter addEditTaskPresenter = new AddEditTaskPresenter(
                null,
                Injection.provideTasksRepository(getApplicationContext()),
                addEditTaskFragment,
                Injection.provideSchedulerProvider());
    }
}
