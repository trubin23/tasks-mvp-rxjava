package ru.trubin23.tasks_mvp_rxjava.addedittask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ru.trubin23.tasks_mvp_rxjava.Injection;
import ru.trubin23.tasks_mvp_rxjava.R;
import ru.trubin23.tasks_mvp_rxjava.util.ActivityUtils;

public class AddEditTaskActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_TASK = 1;

    public static final String EXTRA_TASK_ID = "EXTRA_TASK_ID";

    private AddEditTaskPresenter mAddEditTaskPresenter;

    private ActionBar mActionBar;

    private static final String SHOULD_LOAD_DATA_FROM_REPO = "SHOULD_LOAD_DATA_FROM_REPO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_task_act);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setDisplayShowHomeEnabled(true);
        }

        AddEditTaskFragment addEditTaskFragment = (AddEditTaskFragment)
                getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (addEditTaskFragment == null) {
            addEditTaskFragment = AddEditTaskFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), addEditTaskFragment, R.id.content_frame);
        }

        boolean shouldLoadDataFromRepo = true;

        if (savedInstanceState != null){
            shouldLoadDataFromRepo = savedInstanceState.getBoolean(SHOULD_LOAD_DATA_FROM_REPO);
        }

        mAddEditTaskPresenter = new AddEditTaskPresenter(
                null,
                Injection.provideTasksRepository(getApplicationContext()),
                addEditTaskFragment,
                Injection.provideSchedulerProvider());
    }

    private void setToolbarTitle(@Nullable String taskId){
        if (taskId == null) {
            mActionBar.setTitle(R.string.add_task);
        } else {
            mActionBar.setTitle(R.string.edit_task);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(SHOULD_LOAD_DATA_FROM_REPO, mAddEditTaskPresenter.isDataMissing());
        super.onSaveInstanceState(outState);
    }
}
