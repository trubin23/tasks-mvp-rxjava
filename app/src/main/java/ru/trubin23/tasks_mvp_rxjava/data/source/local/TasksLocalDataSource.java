package ru.trubin23.tasks_mvp_rxjava.data.source.local;

import android.support.annotation.NonNull;

import java.util.List;

import ru.trubin23.tasks_mvp_rxjava.data.Task;
import ru.trubin23.tasks_mvp_rxjava.data.source.TasksDataSource;

public interface TasksLocalDataSource extends TasksDataSource {

    void setTasks(@NonNull List<Task> tasks);
}
