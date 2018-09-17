package ru.trubin23.tasks_mvp_rxjava.data.source;

import android.support.annotation.NonNull;

import com.google.common.base.Optional;

import java.util.List;

import io.reactivex.Flowable;
import ru.trubin23.tasks_mvp_rxjava.data.Task;

interface TasksDataSource {

    Flowable<List<Task>> getTasks();

    Flowable<Optional<Task>> getTask(@NonNull String taskId);
}
