package com.example.passwordstoreapp.Dagger2Staff;

import android.content.Context;
import android.text.SpannableStringBuilder;

import androidx.room.Room;

import com.commonsware.cwac.saferoom.SafeHelperFactory;
import com.example.passwordstoreapp.Database.ObfuscateData;
import com.example.passwordstoreapp.Database.PasswordAppDatabase;
import com.example.passwordstoreapp.Database.PasswordDao;
import com.example.passwordstoreapp.Repository.Repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PasswordDaoModule {
    private Context context;

    public PasswordDaoModule(Context context){
        this.context=context;
    }

    @Provides
    @Singleton
    public SafeHelperFactory safeHelperFactory(){
        return SafeHelperFactory.fromUser(new SpannableStringBuilder(ObfuscateData.getDBPassword()));
    }

    @Provides
    @Singleton
    public PasswordAppDatabase passwordAppDatabase(SafeHelperFactory factory){
        return Room.databaseBuilder(context, PasswordAppDatabase.class,"password")
                .openHelperFactory(factory)
                .build();
    }

    @Provides
    @Singleton
    public PasswordDao passwordDao(PasswordAppDatabase passwordAppDatabase){
        return passwordAppDatabase.passwordDao();
    }

    @Provides
    @Singleton
    public Repository repository(){
        return new Repository();
    }
}
