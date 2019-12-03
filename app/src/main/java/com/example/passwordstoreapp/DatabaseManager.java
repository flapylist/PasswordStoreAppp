package com.example.passwordstoreapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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

    final String TAG="MyLog";
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

    public void deleteItem(Long id) {
        try{
        if (userPasswordDBLongDao == null) {
            Toast.makeText(mcontext,"Record failed",Toast.LENGTH_SHORT).show();
            Log.d(TAG,"No DAO Exist");
            return;
        }
        DeleteBuilder deleteBuilder = userPasswordDBLongDao.deleteBuilder();
        if (id != null) deleteBuilder.where().eq(ID, id);
        deleteBuilder.delete();
        Toast.makeText(mcontext,"Delete complete",Toast.LENGTH_SHORT).show();
        }catch (SQLException e){
            Toast.makeText(mcontext,"Delete failed",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    public void insertItem(UserPasswordDB userPasswordDB, boolean isEdit){
        int count=0;
        String name=userPasswordDB.getName()!=null ? userPasswordDB.getName() : "";
        String login=userPasswordDB.getLogin() !=null ? userPasswordDB.getLogin() : "";
        String password=userPasswordDB.getPassword() !=null ? userPasswordDB.getPassword() : "";
        Long id=userPasswordDB.getId();

        try {
                    userPasswordDBLongDao.createOrUpdate(userPasswordDB);
                    Toast.makeText(mcontext,"Record complete",Toast.LENGTH_SHORT).show();
        }catch (SQLException e){
            Toast.makeText(mcontext,"Record failed",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
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
        Toast.makeText(mcontext,"Clear Data complete",Toast.LENGTH_SHORT).show();
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
