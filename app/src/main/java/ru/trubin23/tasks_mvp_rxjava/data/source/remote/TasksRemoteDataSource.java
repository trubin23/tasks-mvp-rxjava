package ru.trubin23.tasks_mvp_rxjava.data.source.remote;

import android.support.annotation.NonNull;

import ru.trubin23.tasks_mvp_rxjava.data.Task;
import ru.trubin23.tasks_mvp_rxjava.data.source.TasksDataSource;

public interface TasksRemoteDataSource extends TasksDataSource {

    void updateTask(@NonNull Task task);
}
