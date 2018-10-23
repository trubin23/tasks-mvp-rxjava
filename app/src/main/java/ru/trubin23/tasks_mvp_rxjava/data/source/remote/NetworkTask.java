package ru.trubin23.tasks_mvp_rxjava.data.source.remote;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class NetworkTask {

    @SerializedName("id")
    @Expose
    private String mId;
    @SerializedName("title")
    @Expose
    private String mTitle;
    @SerializedName("description")
    @Expose
    private String mDescription;
    @SerializedName("completed")
    @Expose
    private Integer mCompleted;

    public NetworkTask(@NonNull String id, @NonNull String title,
                       @NonNull String description, boolean completed) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mCompleted = StatusOfTask.booleanToInteger(completed);
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Integer getCompleted() {
        return mCompleted;
    }

    public void setCompleted(Integer completed) {
        mCompleted = completed;
    }
}