package ru.trubin23.tasks_mvp_rxjava.tasks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.trubin23.tasks_mvp_rxjava.R;
import ru.trubin23.tasks_mvp_rxjava.addedittask.AddEditTaskActivity;
import ru.trubin23.tasks_mvp_rxjava.data.Task;
import ru.trubin23.tasks_mvp_rxjava.tasks.tasklist.TaskItemListener;
import ru.trubin23.tasks_mvp_rxjava.tasks.tasklist.TasksAdapter;

public class TasksFragment extends Fragment implements TasksContract.View {

    private TasksContract.Presenter mPresenter;

    private TasksAdapter mTasksAdapter;

    private TaskItemListener mTaskItemListener = null;

    private TextView mFilteringLabel;

    private LinearLayout mTasksView;
    private LinearLayout mNoTasksView;
    private ImageView mNoTasksIcon;
    private TextView mNoTasksText;

    @NonNull
    public static TasksFragment newInstance() {
        return new TasksFragment();
    }

    @Override
    public void setPresenter(TasksContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTasksAdapter = new TasksAdapter(new ArrayList<>(0), mTaskItemListener);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tasks_frag, container, false);

        mTasksView = view.findViewById(R.id.list_tasks);
        mNoTasksView = view.findViewById(R.id.no_tasks);
        mNoTasksIcon = mNoTasksView.findViewById(R.id.no_tasks_icon);
        mNoTasksText = mNoTasksView.findViewById(R.id.no_tasks_text);

        mFilteringLabel = view.findViewById(R.id.filtering_label);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(mTasksAdapter);

        Activity activity = getActivity();
        if (activity != null) {
            FloatingActionButton floatingActionButton = activity.findViewById(R.id.fab_add_task);
            if (floatingActionButton != null) {
                floatingActionButton.setOnClickListener(__ -> mPresenter.addNewTask());
            }
        }

        return view;
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.tasks_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_filter:
                showFilteringPopUpMenu();
                return true;
            case R.id.menu_refresh:
                mPresenter.loadTasks(true);
                return true;
            case R.id.menu_clear:
                mPresenter.clearCompletedTask();
                return true;
        }
        return false;
    }

    private void showFilteringPopUpMenu() {
        PopupMenu popupMenu = new PopupMenu(getContext(),
                getActivity().findViewById(R.id.menu_filter));
        popupMenu.getMenuInflater().inflate(R.menu.filter_tasks, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_active:
                    mPresenter.setFiltering(TasksFilterType.ACTIVE_TASKS);
                    break;
                case R.id.menu_completed:
                    mPresenter.setFiltering(TasksFilterType.COMPLETED_TASKS);
                    break;
                default:
                    mPresenter.setFiltering(TasksFilterType.ALL_TASKS);
                    break;
            }
            mPresenter.loadTasks(false);
            return true;
        });

        popupMenu.show();
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        SwipeRefreshLayout swipeRefreshLayout = getView().findViewById(R.id.refresh_layout);
        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(active));
    }

    @Override
    public void showLoadingTasksError() {
        showMessage(getString(R.string.loading_tasks_error));
    }

    @Override
    public void showTasks(List<Task> tasks) {
        mTasksAdapter.setTasks(tasks);

        mTasksView.setVisibility(View.VISIBLE);
        mNoTasksView.setVisibility(View.GONE);
    }

    @Override
    public void showActiveFilterLabel() {
        mFilteringLabel.setText(R.string.label_active);
    }

    @Override
    public void showCompletedFilterLabel() {
        mFilteringLabel.setText(R.string.label_completed);
    }

    @Override
    public void showAllFilterLabel() {
        mFilteringLabel.setText(R.string.label_all);
    }

    @Override
    public void showNoActiveTasks() {
        showNoTasksViews(R.string.no_tasks_active, R.drawable.ic_check_circle);
    }

    @Override
    public void showNoCompletedTasks() {
        showNoTasksViews(R.string.no_tasks_completed, R.drawable.ic_check_box);
    }

    @Override
    public void showNoTasks() {
        showNoTasksViews(R.string.no_tasks_all, R.drawable.ic_verified);
    }

    private void showNoTasksViews(int textRes, int iconRes) {
        mTasksView.setVisibility(View.GONE);

        mNoTasksView.setVisibility(View.VISIBLE);
        mNoTasksIcon.setImageDrawable(getResources().getDrawable(iconRes));
        mNoTasksText.setText(textRes);
    }

    @Override
    public void showCompletedTasksCleared() {
        showMessage(getString(R.string.completed_tasks_cleared));
    }

    @Override
    public void showTaskMarkedComplete() {
        showMessage(getString(R.string.task_marked_complete));
    }

    @Override
    public void showTaskMarkedActive() {
        showMessage(getString(R.string.task_marked_active));
    }

    private void showMessage(String message) {
        View view = getView();
        if (view != null) {
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void showAddTask() {
        Intent intent = new Intent(getContext(), AddEditTaskActivity.class);
        startActivityForResult(intent, AddEditTaskActivity.REQUEST_ADD_TASK);
    }

    @Override
    public void showSuccessfullySavedMessage() {
        showMessage(getString(R.string.successfully_saved_task_message));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }
}
