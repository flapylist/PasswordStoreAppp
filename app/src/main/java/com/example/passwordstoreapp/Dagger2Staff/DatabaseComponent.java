package com.example.passwordstoreapp.Dagger2Staff;

import com.example.passwordstoreapp.ORMLiteCipherStaff.DatabaseManager;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DatabaseManagerModule.class,ContextModule.class})
public interface DatabaseComponent {
    DatabaseManager getManager();
}
