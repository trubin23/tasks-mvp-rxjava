package ru.trubin23.tasks_mvp_rxjava.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.common.base.Optional;
import com.squareup.sqlbrite2.BriteDatabase;
import com.squareup.sqlbrite2.SqlBrite;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import ru.trubin23.tasks_mvp_rxjava.data.Task;
import ru.trubin23.tasks_mvp_rxjava.util.schedulers.BaseSchedulerProvider;

public class TasksLocalRepository implements TasksLocalDataSource {

    @Nullable
    private static TasksLocalRepository INSTANCE;

    @NonNull
    private final BriteDatabase mBriteDatabase;

    private Function<Cursor, Task> mTaskMapperFunction;

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

        mTaskMapperFunction = this::getTask;
    }

    @Override
    public void setTasks(@NonNull List<Task> tasks) {
        mBriteDatabase.delete(Task.TABLE_NAME, null);
        for (Task task : tasks) {
            saveTask(task);
        }
    }

    @Override
    public Flowable<List<Task>> getTasks() {
        String[] projection = {
                Task.COLUMN_ID,
                Task.COLUMN_TITLE,
                Task.COLUMN_DESCRIPTION,
                Task.COLUMN_COMPLETED
        };

        String sql = String.format("SELECT %s FROM %s",
                TextUtils.join(",", projection), Task.TABLE_NAME);

        return mBriteDatabase.createQuery(Task.TABLE_NAME, sql)
                .mapToList(mTaskMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @NonNull
    private Task getTask(@NonNull Cursor cursor) {
        String itemId = cursor.getString(cursor.getColumnIndexOrThrow(Task.COLUMN_ID));
        String title = cursor.getString(cursor.getColumnIndexOrThrow(Task.COLUMN_TITLE));
        String description =
                cursor.getString(cursor.getColumnIndexOrThrow(Task.COLUMN_DESCRIPTION));
        boolean completed =
                cursor.getInt(cursor.getColumnIndexOrThrow(Task.COLUMN_COMPLETED)) == 1;

        return new Task(itemId, title, description, completed);
    }

    @Override
    public Flowable<Optional<Task>> getTask(@NonNull String taskId) {
        String[] projection = {
                Task.COLUMN_ID,
                Task.COLUMN_TITLE,
                Task.COLUMN_DESCRIPTION,
                Task.COLUMN_COMPLETED
        };

        String sql = String.format("SELECT %s FROM %s WHERE %s LIKE ?",
                TextUtils.join(",", projection), Task.TABLE_NAME, Task.COLUMN_ID);

        return mBriteDatabase.createQuery(Task.TABLE_NAME, sql, taskId)
                .mapToOneOrDefault(cursor -> Optional.of(mTaskMapperFunction.apply(cursor)),
                        Optional.<Task>absent())
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public void saveTask(@NonNull Task task) {
        ContentValues values = new ContentValues();
        values.put(Task.COLUMN_ID, task.getTaskId());
        values.put(Task.COLUMN_TITLE, task.getTitle());
        values.put(Task.COLUMN_DESCRIPTION, task.getDescription());
        values.put(Task.COLUMN_COMPLETED, task.isCompleted());
        mBriteDatabase.insert(Task.TABLE_NAME, values, SQLiteDatabase.CONFLICT_REPLACE);
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
        String selection = Task.COLUMN_COMPLETED + " LIKE ?";
        String[] selectionArgs = {"1"};
        mBriteDatabase.delete(Task.TABLE_NAME, selection, selectionArgs);
    }
}
