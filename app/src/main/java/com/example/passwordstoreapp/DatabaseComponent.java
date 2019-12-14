package com.example.passwordstoreapp;

import com.j256.ormlite.dao.Dao;

import dagger.Component;

@DatabaseManagerScope
@Component(modules = DatabaseManagerModule.class)
public interface DatabaseComponent {
    DatabaseManager getManager();
}
