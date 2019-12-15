package com.example.passwordstoreapp.Dagger2Staff;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {
    private final Context context;

    public ContextModule(Context context){
        this.context=context;
    }

    @Singleton
    @Provides
    public Context context(){
        return context.getApplicationContext();
    }
}
