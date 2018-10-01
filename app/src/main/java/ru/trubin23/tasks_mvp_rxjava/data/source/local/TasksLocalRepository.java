package ru.trubin23.tasks_mvp_rxjava.data.source.local;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.common.base.Optional;

import java.util.List;

import io.reactivex.Flowable;
import ru.trubin23.tasks_mvp_rxjava.data.Task;

public class TasksLocalRepository implements TasksLocalDataSource {

    @Override
    public void setTasks(@NonNull List<Task> tasks) {

    }

    @Override
    public Flowable<List<Task>> getTasks() {
        String[] projection = {
                TasksDbHelper.COLUMN_ID,
                TasksDbHelper.COLUMN_TITLE,
                TasksDbHelper.COLUMN_DESCRIPTION,
                TasksDbHelper.COLUMN_COMPLETED
        };
        String sql = String.format("SELECT %s FROM %s",
                TextUtils.join(",", projection), TasksDbHelper.TABLE_NAME)
        return null;
    }

    @Override
    public Flowable<Optional<Task>> getTask(@NonNull String taskId) {
        return null;
    }

    @Override
    public void saveTask(@NonNull Task task) {

    }

    @Override
    public void updateTask(@NonNull Task task) {

    }

    @Override
    public void deleteTask(@NonNull String taskId) {

    }

    @Override
    public void completedTask(@NonNull String taskId, boolean completed) {

    }

    @Override
    public void clearCompletedTask() {

    }
}
