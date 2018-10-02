package ru.trubin23.tasks_mvp_rxjava.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.common.base.Optional;
import com.squareup.sqlbrite2.BriteDatabase;
import com.squareup.sqlbrite2.SqlBrite;

import java.util.List;

import io.reactivex.Flowable;
import ru.trubin23.tasks_mvp_rxjava.data.Task;
import ru.trubin23.tasks_mvp_rxjava.util.schedulers.BaseSchedulerProvider;

public class TasksLocalRepository implements TasksLocalDataSource {

    @Nullable
    private static TasksLocalRepository INSTANCE;

    @NonNull
    private final BriteDatabase mBriteDatabase;

    public static TasksLocalRepository getInstance(
            @NonNull Context context,
            @NonNull BaseSchedulerProvider schedulerProvider) {

        if (INSTANCE == null) {
            INSTANCE = new TasksLocalRepository(context, schedulerProvider);
        }
        return INSTANCE;
    }

    private TasksLocalRepository(@NonNull Context context,
                                 @NonNull BaseSchedulerProvider schedulerProvider) {

        TasksDbHelper dbHelper = new TasksDbHelper(context);
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        mBriteDatabase = sqlBrite.wrapDatabaseHelper(dbHelper, schedulerProvider.io());
    }

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
