package com.example.passwordstoreapp;

import android.content.Context;

import com.j256.ormlite.cipher.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private final Context mcontext;
    private static DatabaseManager INSTANCE;
    private DatabaseHelper databaseHelper;

    private Dao<UserPasswordDB,Long> userPasswordDBLongDao;
    private static String NAME="name";
    private static String LOGIN="login";
    private static String PASSWORD="password";
    private static String ID="id";

    public DatabaseManager(Context context){
        mcontext=context;
        databaseHelper= OpenHelperManager.getHelper(mcontext,DatabaseHelper.class);

            userPasswordDBLongDao=databaseHelper.getItemDao();
    }

    public static DatabaseManager getInstance(Context context){
        if(INSTANCE==null)INSTANCE=new DatabaseManager(context);
        return INSTANCE;
    }

    public int deleteItem(Long id) {
        try{
        if (userPasswordDBLongDao == null) return -1;
        DeleteBuilder deleteBuilder = userPasswordDBLongDao.deleteBuilder();
        if (id != null) deleteBuilder.where().eq(ID, id);
        deleteBuilder.delete();
        return 0;
    }catch (SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    public boolean isItemExist(Long id) {
        QueryBuilder queryBuilder = userPasswordDBLongDao.queryBuilder();
        boolean isExist = false;
        try{
        if (queryBuilder.where().eq(ID, id).countOf() > 0) isExist = true;
        else isExist = false;
    }catch (SQLException e) {
            e.printStackTrace();
        }
        return isExist;
    }

    public int insertItem(UserPasswordDB userPasswordDB, boolean isEdit){
        int count=0;
        String name=userPasswordDB.getName()!=null ? userPasswordDB.getName() : "";
        String login=userPasswordDB.getLogin() !=null ? userPasswordDB.getLogin() : "";
        String password=userPasswordDB.getPassword() !=null ? userPasswordDB.getPassword() : "";
        Long id=userPasswordDB.getId();

        try {

            if(isItemExist(id)){
                if(isEdit){
                    deleteItem(id);
                    userPasswordDBLongDao.create(userPasswordDB);
                    count=1;
                }else {
                    userPasswordDBLongDao.create(userPasswordDB);
                    count=0;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
            count=-1;
            return count;
        }
        return count;
    }

    public void releaseData(){
        if(databaseHelper!=null){
            OpenHelperManager.releaseHelper();
            databaseHelper=null;
            INSTANCE=null;
        }
    }

    public void clearData(){
        databaseHelper.clearTable();
    }

    public List<UserPasswordDB> getAllItems(){
        try {
            return userPasswordDBLongDao.queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
