package ru.trubin23.tasks_mvp_rxjava.tasks;

import android.support.v4.app.Fragment;

public class TasksFragment extends Fragment implements TasksContract.View{

    private TasksContract.Presenter mPresenter;

    @Override
    public void setPresenter(TasksContract.Presenter presenter) {

    }
}
