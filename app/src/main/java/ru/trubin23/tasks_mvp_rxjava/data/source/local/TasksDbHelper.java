package ru.trubin23.tasks_mvp_rxjava.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.trubin23.tasks_mvp_rxjava.data.Task;

public class TasksDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Tasks.db";

    private static final int DATABASE_VERSION = 1;

    private static final String TEXT_TYPE = " TEXT";
    private static final String BOOLEAN_TYPE = " INTEGER";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Task.TABLE_NAME + " (" +
                    Task.COLUMN_ID + TEXT_TYPE + " PRIMARY KEY," +
                    Task.COLUMN_TITLE + TEXT_TYPE + "," +
                    Task.COLUMN_DESCRIPTION + TEXT_TYPE + "," +
                    Task.COLUMN_COMPLETED + BOOLEAN_TYPE + ")";

    public TasksDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
