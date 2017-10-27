package com.projects.personal.denis.testprojectcontactlist;

import android.app.Application;

import com.projects.personal.denis.testprojectcontactlist.models.MyObjectBox;

import io.objectbox.BoxStore;

public class App extends Application {
    private static final String TAG = App.class.getSimpleName();

    private BoxStore boxStore;

    @Override
    public void onCreate() {
        super.onCreate();

        boxStore = MyObjectBox.builder().androidContext(App.this).build();
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }
}
