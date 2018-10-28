package ru.trubin23.tasks_mvp_rxjava.taskdetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import ru.trubin23.tasks_mvp_rxjava.R;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.task_detail_frag, container, false);

        setHasOptionsMenu(true);

        //getActivity().findViewById((R.id.fab_edit_task)
        //        .setOnClickListener{() -> mPresenter.editTask() }

        return root;
    }

    public boolean onOptionsItemSelected(MenuItem item)  {
        boolean deletePressed = item.getItemId() == R.id.menu_delete;
        if (deletePressed) {
            mPresenter.deleteTask();
        }
        return deletePressed;
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

    @Override
    public void showMissingTask() {

    }
}
