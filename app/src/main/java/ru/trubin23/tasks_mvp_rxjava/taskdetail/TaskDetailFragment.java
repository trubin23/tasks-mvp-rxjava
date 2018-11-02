package ru.trubin23.tasks_mvp_rxjava.taskdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.trubin23.tasks_mvp_rxjava.R;
import ru.trubin23.tasks_mvp_rxjava.addedittask.AddEditTaskActivity;

public class TaskDetailFragment extends Fragment implements TaskDetailContract.View {

    private static final int REQUEST_EDIT_TASK = 1;

    private TaskDetailContract.Presenter mPresenter;

    TextView mTitle;
    TextView mDescription;

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.task_detail_frag, container, false);

        setHasOptionsMenu(true);

        mTitle = root.findViewById(R.id.task_detail_title);
        mDescription = root.findViewById(R.id.task_detail_description);

        getActivity().findViewById(R.id.fab_edit_task)
                .setOnClickListener(view -> mPresenter.editTask());

        return root;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
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
        mTitle.setText("");
        mDescription.setText(getString(R.string.no_data));
    }

    @Override
    public void showEditTask(String taskId) {
        Intent intent = new Intent(getContext(), AddEditTaskActivity.class);
        intent.putExtra(AddEditTaskActivity.EXTRA_TASK_ID, taskId);
        startActivityForResult(intent, REQUEST_EDIT_TASK);
    }

    @Override
    public void showTaskDeleted(String taskId) {

    }
}
