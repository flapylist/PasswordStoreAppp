package com.example.passwordstoreapp.Repository;

import androidx.lifecycle.LiveData;

import com.example.passwordstoreapp.Dagger2Staff.DaggerPasswordComponent;
import com.example.passwordstoreapp.Dagger2Staff.PasswordComponent;
import com.example.passwordstoreapp.Dagger2Staff.PasswordDaoModule;
import com.example.passwordstoreapp.Database.Password;
import com.example.passwordstoreapp.Database.PasswordDao;
import com.example.passwordstoreapp.ReleaseApp;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;

public class Repository {
    PasswordComponent component= DaggerPasswordComponent.builder()
            .passwordDaoModule(new PasswordDaoModule(ReleaseApp.getInstance().getApplicationContext()))
            .build();
    PasswordDao dao=component.getDao();

    public Single<Long> insert(Password password){
        return Single.create(emitter -> {
            emitter.onSuccess(dao.insert(password));
        });
    }

    public Single<Integer> update(Password password){
        return Single.create(emitter -> {
            emitter.onSuccess(dao.update(password));
        });
    }

    public Single<Integer> delete(Password password){
        return Single.create(emitter -> {
            emitter.onSuccess(dao.delete(password));
        });
    }

    public Single<List<Password>> getAll(){
        return Single.create(emitter -> {
            emitter.onSuccess(dao.getAll());
        });
    }
}
