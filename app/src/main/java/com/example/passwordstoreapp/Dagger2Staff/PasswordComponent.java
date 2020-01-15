package com.example.passwordstoreapp.Dagger2Staff;

import com.example.passwordstoreapp.Database.PasswordDao;
import com.example.passwordstoreapp.Repository.Repository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = PasswordDaoModule.class)
public interface PasswordComponent {
    PasswordDao getDao();
    Repository getRepo();
}
