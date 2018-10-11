package ru.trubin23.tasks_mvp_rxjava.addedittask;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

public class AddEditTaskFragment extends Fragment implements AddEditTaskContract.View {

    private AddEditTaskContract.Presenter mPresenter;

    @NonNull
    public static AddEditTaskFragment newInstance() {
        return new AddEditTaskFragment();
    }

    @Override
    public void setPresenter(AddEditTaskContract.Presenter presenter) {
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
