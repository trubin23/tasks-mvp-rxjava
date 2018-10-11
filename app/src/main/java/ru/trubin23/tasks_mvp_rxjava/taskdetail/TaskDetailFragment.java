package ru.trubin23.tasks_mvp_rxjava.taskdetail;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

public class TaskDetailFragment extends Fragment implements TaskDetailContract.View {

    private TaskDetailContract.Presenter mPresenter;

    @NonNull
    public static TaskDetailFragment newInstance() {
        return new TaskDetailFragment();
    }

    @Override
    public void setPresenter(TaskDetailContract.Presenter presenter) {
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
