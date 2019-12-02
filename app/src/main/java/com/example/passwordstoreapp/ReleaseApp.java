package com.example.passwordstoreapp;

import android.app.Application;

import net.sqlcipher.database.SQLiteDatabase;

public class ReleaseApp extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        SQLiteDatabase.loadLibs(this);
    }
}
