package ru.trubin23.tasks_mvp_rxjava.data.source.cache;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ru.trubin23.tasks_mvp_rxjava.data.Task;

public class TasksCacheRepository implements TasksCacheDataSource {

    private static TasksCacheRepository INSTANCE;

    private Map<String, Task> mCachedTask;

    private boolean mCacheIsDirty = false;

    private TasksCacheRepository() {
        mCachedTask = new LinkedHashMap<>();
    }

    public static TasksCacheRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (TasksCacheRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TasksCacheRepository();
                }
            }
        }
        return INSTANCE;
    }

    private boolean cacheNotAvailable() {
        return mCacheIsDirty || mCachedTask == null || mCachedTask.isEmpty();
    }

    @Override
    public void setTasks(@NonNull List<Task> tasks) {
        if (mCachedTask == null) {
            mCachedTask = new LinkedHashMap<>();
        }
        mCachedTask.clear();

        for (Task task : tasks) {
            mCachedTask.put(task.getTaskId(), task);
        }
        mCacheIsDirty = false;
    }

    @Override
    public void irrelevantState() {
        mCacheIsDirty = true;
    }

    @Nullable
    @Override
    public List<Task> getTasks() {
        if (cacheNotAvailable()) {
            return null;
        } else {
            return new ArrayList<>(mCachedTask.values());
        }
    }

    @Nullable
    @Override
    public Task getTaskById(@NonNull String taskId) {
        if (cacheNotAvailable()) {
            return null;
        } else {
            return mCachedTask.get(taskId);
        }
    }

    @Override
    public void addTask(@NonNull Task task) {
        if (mCachedTask == null) {
            mCachedTask = new LinkedHashMap<>();
        }
        mCachedTask.put(task.getTaskId(), task);
    }

    @Override
    public void removeTask(@NonNull String taskId) {
        if (mCachedTask != null) {
            mCachedTask.remove(taskId);
        }
    }

    @Override
    public void completedTask(@NonNull String taskId, boolean completed) {
        Task task = getTaskById(taskId);
        if (task != null){
            Task cacheTask = new Task(task.getTitle(), task.getDescription(),
                    task.getTaskId(), completed);
            addTask(cacheTask);
        }
    }

    @Override
    public void clearCompletedTask() {
        if (mCachedTask == null){
            mCachedTask = new LinkedHashMap<>();
        }

        Iterator<Map.Entry<String, Task>> iterator = mCachedTask.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Task> entry = iterator.next();
            if (entry.getValue().isCompleted()){
                iterator.remove();
            }
        }
    }
}