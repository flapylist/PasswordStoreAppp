package com.example.passwordstoreapp;

import android.content.Context;


import com.j256.ormlite.cipher.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME="PasswordDB";
    private static final int DATABASE_VERSION=1;

    private Dao<UserPasswordDB,Long> UserPasswordDao;

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    protected String getPassword(){
        return ObfuscateData.getDBPassword();
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource){
        try{
            TableUtils.createTable(connectionSource,UserPasswordDB.class);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion){

    }

    public boolean isTableExist(SQLiteDatabase db, String tableName){
        Cursor c=null;
        boolean isExist=false;
        try{
            c=db.query(tableName,null,null,null,null,null,null);
            isExist=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return isExist;
    }

    @Override
    public void close(){
        UserPasswordDao=null;
        super.close();
    }

    public Dao<UserPasswordDB,Long> getItemDao(){
        try {
            if (UserPasswordDao == null) UserPasswordDao = getDao(UserPasswordDB.class);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return UserPasswordDao;
    }

    public void clearTable(){
        try {
            TableUtils.clearTable(getConnectionSource(), UserPasswordDB.class);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
