package ru.trubin23.tasks_mvp_rxjava.data.source.remote;

import android.support.annotation.NonNull;

import com.google.common.base.Optional;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import ru.trubin23.tasks_mvp_rxjava.data.Task;
import ru.trubin23.tasks_mvp_rxjava.data.source.TasksDataSource;

public class TasksRemoteRepository implements TasksDataSource {

    private static TasksRemoteRepository INSTANCE;

    private TasksRemoteRepository() {

    }

    public static TasksRemoteRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TasksRemoteRepository();
        }
        return INSTANCE;
    }


    @Override
    public Flowable<List<Task>> getTasks() {
        return RetrofitClient.getTasks()
                .flatMap(networkTasks -> Flowable.fromIterable(networkTasks)
                        .map(networkTask -> new Task(networkTask.getId(),
                                networkTask.getTitle(), networkTask.getDescription(),
                                StatusOfTask.integerToBoolean(networkTask.getCompleted()))
                        )
                        .toList()
                        .toFlowable());
    }

    @Override
    public Flowable<Optional<Task>> getTask(@NonNull String taskId) {

        NetworkTask networkTask = RetrofitClient.getTask(taskId).singleOrError().blockingGet();

        Task task = new Task(networkTask.getId(),
                networkTask.getTitle(), networkTask.getDescription(),
                StatusOfTask.integerToBoolean(networkTask.getCompleted()));

        Optional<Task> taskOptional = Optional.of(task);

        return Single.just(taskOptional).toFlowable();
    }

    @Override
    public void saveTask(@NonNull Task task) {
        NetworkTask networkTask = new NetworkTask(task.getId(),
                task.getTitle(), task.getDescription(),
                task.isCompleted());

        RetrofitClient.addTask(networkTask, new ProcessingResponse<>());
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
