package com.example.passwordstoreapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.michaelrocks.paranoid.Obfuscate;
@Obfuscate
public class MainActivity extends AppCompatActivity {
    List<UserPasswordDB> userPasswordDBList;
    Context mcontext;
    RecyclerView recyclerView;
    PasswordRecyclerAdapter adapter;
    FloatingActionButton ftbtn;
    CoordinatorLayout cl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_listview);
        EventBus.getDefault().register(this);
        userPasswordDBList=getAllUsers();
        mcontext=this;

        ftbtn=findViewById(R.id.floating_action_button);
        ftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAlert(mcontext);
            }
        });

        cl=findViewById(R.id.coordinator);
        recyclerView=findViewById(R.id.recyclerView);

        adapter=new PasswordRecyclerAdapter(this, userPasswordDBList);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);

        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddEvent(AddEvent event){
        editAlert(mcontext,event.item, event.position);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeleteEvent(DeleteEvent event){
        final UserPasswordDB userPasswordDB=userPasswordDBList.get(event.position);
        deleteItem(userPasswordDB);
        adapter.deleteItem(event.position);

        showUndoSnackbar(userPasswordDB,event.position);
    }

    private void showUndoSnackbar(final UserPasswordDB item, final int position){
        Snackbar snackbar=Snackbar.make(cl,"Item deleted",Snackbar.LENGTH_LONG);
        snackbar.setAction("Return delete", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(item);
                adapter.addItem(item,position);

            }
        })
                .show();
    }




    public void addAlert(Context context){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        final EditText etName,etLogin,etPassword;
        etName=new EditText(context);
        etName.setWidth(80);
        etName.setHint(R.string.nameHint);
        etName.requestFocus();
        etLogin=new EditText(context);
        etLogin.setWidth(80);
        etLogin.setHint(R.string.loginHint);
        etPassword=new EditText(context);
        etPassword.setWidth(80);
        etPassword.setHint(R.string.passwordHint);
        etPassword.setTransformationMethod(new PasswordTransformationMethod());

        LinearLayout linearLayout=new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(etName);
        linearLayout.addView(etLogin);
        linearLayout.addView(etPassword);

        builder.setTitle(R.string.AddAlertTitle)
                .setView(linearLayout)
                .setPositiveButton(R.string.SaveField, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserPasswordDB userPasswordDB=new UserPasswordDB();
                        if(etName!=null && etLogin!=null && etPassword!=null) {
                            userPasswordDB.setName(etName.getText().toString());
                            userPasswordDB.setLogin(etLogin.getText().toString());
                            userPasswordDB.setPassword(etPassword.getText().toString());
                            addItem(userPasswordDB);
                            adapter.addItem(userPasswordDB);

                        }
                    }
                })
                .setNegativeButton(R.string.CancelField, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert=builder.create();
        alert.show();

    }


    public void editAlert(Context context, final UserPasswordDB userPasswordDB, final int position){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        final EditText etName,etLogin,etPassword;
        etName=new EditText(context);
        etName.setWidth(80);
        etName.setText(userPasswordDB.getName().toString());
        etName.requestFocus();
        etLogin=new EditText(context);
        etLogin.setWidth(80);
        etLogin.setText(userPasswordDB.getLogin().toString());
        etPassword=new EditText(context);
        etPassword.setWidth(80);
        etPassword.setText(userPasswordDB.getPassword().toString());
        etPassword.setTransformationMethod(new PasswordTransformationMethod());

        LinearLayout linearLayout=new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(etName);
        linearLayout.addView(etLogin);
        linearLayout.addView(etPassword);

        builder.setTitle(R.string.EditAlertTitle)
                .setView(linearLayout)
                .setPositiveButton(R.string.SaveField, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(etName!=null && etLogin!=null && etPassword!=null) {
                            userPasswordDB.setName(etName.getText().toString());
                            userPasswordDB.setLogin(etLogin.getText().toString());
                            userPasswordDB.setPassword(etPassword.getText().toString());
                            addItem(userPasswordDB);
                            adapter.notifyItemChanged(position);

                        }
                    }
                })
                .setNegativeButton(R.string.CancelField, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert=builder.create();
        alert.show();
    }

    public void addItem(UserPasswordDB userPasswordDB){
        DatabaseManager.getInstance(getApplicationContext()).insertItem(userPasswordDB);
    }

    public void deleteItem(UserPasswordDB userPasswordDB){
        DatabaseManager.getInstance(getApplicationContext()).deleteItem(userPasswordDB.getId());
    }
    public List<UserPasswordDB> getAllUsers(){
        return DatabaseManager.getInstance(getApplicationContext()).getAllItems();
    }

    protected void onDestroy(Bundle bundle){
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
