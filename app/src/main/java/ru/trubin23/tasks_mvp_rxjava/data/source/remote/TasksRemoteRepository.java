package ru.trubin23.tasks_mvp_rxjava.data.source.remote;

import android.support.annotation.NonNull;

import com.google.common.base.Optional;

import java.util.List;

import io.reactivex.Flowable;
import ru.trubin23.tasks_mvp_rxjava.data.Task;
import ru.trubin23.tasks_mvp_rxjava.data.source.TasksDataSource;

public class TasksRemoteRepository implements TasksDataSource {

    private TasksRemoteRepository() {

    }

    public static TasksRemoteRepository getInstance() {
        return new TasksRemoteRepository();
    }


    @Override
    public Flowable<List<Task>> getTasks() {
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
    public void completeTask(@NonNull Task task) {

    }

    @Override
    public void completeTask(@NonNull String taskId) {

    }

    @Override
    public void activateTask(@NonNull Task task) {

    }

    @Override
    public void activateTask(@NonNull String taskId) {

    }

    @Override
    public void clearCompletedTask() {

    }

    @Override
    public void refreshTasks() {

    }

    @Override
    public void deleteAllTasks() {

    }

    @Override
    public void deleteTask(@NonNull String taskId) {

    }
}
