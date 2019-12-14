package com.example.passwordstoreapp;

import android.content.Context;

import com.j256.ormlite.cipher.android.apptools.OpenHelperManager;
import com.j256.ormlite.cipher.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;

import dagger.Module;
import dagger.Provides;

@Module(includes = ContextModule.class)
public class DatabaseManagerModule {

    @DatabaseManagerScope
    @Provides
    public DatabaseManager databaseManager(Context context){
        return new DatabaseManager(context);
    }
}
