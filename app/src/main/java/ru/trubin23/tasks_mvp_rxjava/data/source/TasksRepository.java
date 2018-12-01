package ru.trubin23.tasks_mvp_rxjava.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Optional;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import ru.trubin23.tasks_mvp_rxjava.data.Task;

public class TasksRepository implements TasksDataSource {

    @Nullable
    private static TasksRepository INSTANCE = null;

    private final TasksDataSource mTasksRemoteDataSource;
    private final TasksDataSource mTasksLocalDataSource;

    private Map<String, Task> mCachedTasks;

    private boolean mCacheIsDirty = false;

    private TasksRepository(@NonNull TasksDataSource tasksRemoteDataSource,
                            @NonNull TasksDataSource tasksLocalDataSource) {
        mTasksRemoteDataSource = tasksRemoteDataSource;
        mTasksLocalDataSource = tasksLocalDataSource;
    }

    public static TasksRepository getInstance(@NonNull TasksDataSource tasksRemoteDataSource,
                                              @NonNull TasksDataSource tasksLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new TasksRepository(tasksRemoteDataSource, tasksLocalDataSource);
        }
        return INSTANCE;
    }

    @Override
    public Flowable<List<Task>> getTasks() {
        if (mCachedTasks != null && !mCacheIsDirty) {
            return Flowable.fromIterable(mCachedTasks.values()).toList().toFlowable();
        } else if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<>();
        }

        Flowable<List<Task>> remoteTasks = getAndSaveRemoteTasks();

        if (mCacheIsDirty) {
            return remoteTasks;
        } else {
            Flowable<List<Task>> localTasks = getAndCacheLocalTasks();
            return Flowable.concat(localTasks, remoteTasks)
                    .filter(tasks -> !tasks.isEmpty())
                    .firstOrError()
                    .toFlowable();
        }
    }

    private Flowable<List<Task>> getAndCacheLocalTasks() {
        return mTasksLocalDataSource.getTasks()
                .flatMap(tasks -> Flowable.fromIterable(tasks)
                        .doOnNext(task -> mCachedTasks.put(task.getId(), task))
                        .toList()
                        .toFlowable());
    }

    private Flowable<List<Task>> getAndSaveRemoteTasks() {
        return mTasksRemoteDataSource.getTasks()
                .flatMap(tasks -> Flowable.fromIterable(tasks)
                        .doOnNext(task -> {
                            mTasksLocalDataSource.saveTask(task);
                            mCachedTasks.put(task.getId(), task);
                        })
                        .toList()
                        .toFlowable())
                .doOnComplete(() -> mCacheIsDirty = false);
    }

    @Override
    public Flowable<Optional<Task>> getTask(@NonNull String taskId) {
        return mTasksLocalDataSource
                .getTask(taskId)
                .doOnNext(taskOptional -> {
                    if (taskOptional.isPresent()){
                        mCachedTasks.put(taskId, taskOptional.get());
                    }
                })
                .firstElement().toFlowable();
    }

    @Override
    public void saveTask(@NonNull Task task) {
        mTasksRemoteDataSource.saveTask(task);
        mTasksLocalDataSource.saveTask(task);

        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<>();
        }
        mCachedTasks.put(task.getId(), task);
    }

    @Override
    public void completeTask(@NonNull Task task) {
        mTasksRemoteDataSource.completeTask(task);
        mTasksLocalDataSource.completeTask(task);

        Task completedTask = new Task(task.getId(), task.getTitle(), task.getDescription(), true);

        if (mCachedTasks == null){
            mCachedTasks = new LinkedHashMap<>();
        }
        mCachedTasks.put(task.getId(), completedTask);
    }

    @Override
    public void completeTask(@NonNull String taskId) {
        Task taskWithId = getTaskWithId(taskId);
        if (taskWithId != null){
            completeTask(taskWithId);
        }
    }

    @Override
    public void activateTask(@NonNull Task task) {
        mTasksRemoteDataSource.activateTask(task);
        mTasksLocalDataSource.activateTask(task);

        Task activeTask = new Task(task.getId(), task.getTitle(), task.getDescription(), false);

        if (mCachedTasks == null){
            mCachedTasks = new LinkedHashMap<>();
        }
        mCachedTasks.put(task.getId(), activeTask);
    }

    @Override
    public void activateTask(@NonNull String taskId) {
        Task taskWithId = getTaskWithId(taskId);
        if (taskWithId != null){
            activateTask(taskWithId);
        }
    }

    private Task getTaskWithId(@NonNull String taskId) {
        if (mCachedTasks == null || mCachedTasks.isEmpty()) {
            return null;
        } else {
            return mCachedTasks.get(taskId);
        }
    }

    @Override
    public void refreshTasks() {
        mCacheIsDirty = true;
    }

    @Override
    public void deleteAllTasks() {
        mTasksRemoteDataSource.deleteAllTasks();
        mTasksLocalDataSource.deleteAllTasks();

        if (mCachedTasks ==null){
            mCachedTasks = new LinkedHashMap<>();
        }
        mCachedTasks.clear();
    }

    @Override
    public void deleteTask(@NonNull String taskId) {
        mTasksRemoteDataSource.deleteTask(taskId);
        mTasksLocalDataSource.deleteTask(taskId);

        if (mCachedTasks != null) {
            mCachedTasks.remove(taskId);
        }
    }

    @Override
    public void clearCompletedTask() {
        mTasksRemoteDataSource.clearCompletedTask();
        mTasksLocalDataSource.clearCompletedTask();

        if (mCachedTasks == null){
            mCachedTasks = new LinkedHashMap<>();
        }
        Iterator<Map.Entry<String, Task>> iterator = mCachedTasks.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Task> entry = iterator.next();
            if (entry.getValue().isCompleted()){
                iterator.remove();
            }
        }
    }
}
