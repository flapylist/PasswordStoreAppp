package com.example.passwordstoreapp.Dagger2Staff;

import android.content.Context;

import com.example.passwordstoreapp.ORMLiteCipherStaff.DatabaseManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseManagerModule {

    @Singleton
    @Provides
    public DatabaseManager databaseManager(Context context){
        return new DatabaseManager(context);
    }
}
