package com.appwallah.ideas;

import android.app.Application;

import com.firebase.client.Firebase;

public class IdeasApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
