package com.example.passwordstoreapp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListActivity extends AppCompatActivity {
    List<UserPasswordDB> userPasswordDBList;
    Context mcontext;
    RecyclerView recyclerView;
    PasswordRecyclerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_listview);

        recyclerView=findViewById(R.id.recyclerView);
        userPasswordDBList=getAllUsers();
        adapter=new PasswordRecyclerAdapter(this,userPasswordDBList);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    public void presentAlert(final String name, final String login, final String password, final Long id){
        final EditText etName, etLogin, etPassword;
        etName=new EditText(this);
        etLogin=new EditText(this);
        etPassword=new EditText(this);

        etName.setText(name);
        etName.setWidth(100);
        etLogin.setText(login);
        etLogin.setWidth(100);
        etPassword.setText(password);
        etPassword.setWidth(100);

        LinearLayout linearLayout=new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(etName);
        linearLayout.addView(etLogin);
        linearLayout.addView(etPassword);

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Edit")
                .setView(linearLayout)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserPasswordDB userPasswordDB=new UserPasswordDB();
                        userPasswordDB.setName(etName.getText().toString());
                        userPasswordDB.setLogin(etLogin.getText().toString());
                        userPasswordDB.setPassword(etPassword.getText().toString());
                        userPasswordDB.setId(id);

                        DatabaseManager.getInstance(getApplicationContext()).insertItem(userPasswordDB,true);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert=builder.create();
        alert.show();
    }

    public List<UserPasswordDB> getAllUsers(){
        return DatabaseManager.getInstance(getApplicationContext()).getAllItems();
    }
}
