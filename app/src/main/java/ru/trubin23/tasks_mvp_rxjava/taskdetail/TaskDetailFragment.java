package ru.trubin23.tasks_mvp_rxjava.taskdetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import ru.trubin23.tasks_mvp_rxjava.R;
import ru.trubin23.tasks_mvp_rxjava.addedittask.AddEditTaskActivity;

public class TaskDetailFragment extends Fragment implements TaskDetailContract.View {

    private static final int REQUEST_EDIT_TASK = 1;

    private TaskDetailContract.Presenter mPresenter;

    TextView mTitle;
    TextView mDescription;
    CheckBox mCompleteStatus;

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
        mCompleteStatus = root.findViewById(R.id.task_detail_complete);

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
    public void showCompletionStatus(boolean complete) {
        mCompleteStatus.setChecked(complete);
    }

    @Override
    public void showEditTask(String taskId) {
        Intent intent = new Intent(getContext(), AddEditTaskActivity.class);
        intent.putExtra(AddEditTaskActivity.EXTRA_TASK_ID, taskId);
        startActivityForResult(intent, REQUEST_EDIT_TASK);
    }

    @Override
    public void showTaskDeleted(String taskId) {
        Activity activity = getActivity();
        if (activity != null){
            activity.finish();
        }
    }

    @Override
    public void showTaskMarkedComplete() {
        Snackbar.make(getView(),getString(R.string.task_marked_complete), Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void showTaskMarkedActive() {
        Snackbar.make(getView(),getString(R.string.task_marked_active), Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_EDIT_TASK){// && resultCode == ADD_RESULT_OK){
            getActivity().finish();
        }
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void hideTitle() {
        mDescription.setVisibility(View.GONE);
    }

    @Override
    public void showTitle(@NonNull String title) {
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText(title);
    }

    @Override
    public void hideDescription() {
        mDescription.setVisibility(View.GONE);
    }

    @Override
    public void showDescription(@NonNull String description) {
        mDescription.setVisibility(View.VISIBLE);
        mDescription.setText(description);
    }
}
