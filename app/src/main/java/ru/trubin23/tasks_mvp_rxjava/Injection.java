package ru.trubin23.tasks_mvp_rxjava;

import android.content.Context;
import android.support.annotation.NonNull;

import ru.trubin23.tasks_mvp_rxjava.data.source.TasksRepository;
import ru.trubin23.tasks_mvp_rxjava.util.schedulers.BaseSchedulerProvider;
import ru.trubin23.tasks_mvp_rxjava.util.schedulers.SchedulerProvider;

public class Injection {

    public static TasksRepository provideTasksRepository(@NonNull Context context){
        return new TasksRepository();
    }

    public static BaseSchedulerProvider provideSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }
}
