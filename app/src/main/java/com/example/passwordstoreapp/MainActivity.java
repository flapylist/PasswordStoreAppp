package com.example.passwordstoreapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText etName, etLogin, etPassword;
    Button btnAdd, btnViewList;
    List<UserPasswordDB> userPasswordDBList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName=findViewById(R.id.etName);
        etLogin=findViewById(R.id.etLogin);
        etPassword=findViewById(R.id.etPassword);

        btnAdd=findViewById(R.id.btnAdd);
        btnViewList=findViewById(R.id.btnViewAll);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        btnViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ListActivity.class);
                startActivity(intent);
            }
        });
    }

    public void addItem(){
        String name=etName.getText().toString();
        String login=etLogin.getText().toString();
        String password=etPassword.getText().toString();
        if((name!=null && !name.isEmpty()) && (login!=null && !login.isEmpty()) && (password!=null && !password.isEmpty())) {
            UserPasswordDB userPasswordDB = new UserPasswordDB();

            userPasswordDB.setName(name);
            userPasswordDB.setLogin(login);
            userPasswordDB.setPassword(password);
            addItemData(userPasswordDB);
            userPasswordDBList=getAllItems();
        }
    }

    public void addItemData(UserPasswordDB userPasswordDB){
        int isSucces=DatabaseManager.getInstance(getApplicationContext()).insertItem(userPasswordDB,false);
        if(isSucces==1) Toast.makeText(this,"Record complete", Toast.LENGTH_SHORT).show();
        else Toast.makeText(this,"Record failed",Toast.LENGTH_SHORT).show();
    }

    public List<UserPasswordDB> getAllItems(){
        return DatabaseManager.getInstance(getApplicationContext()).getAllItems();
    }
}
