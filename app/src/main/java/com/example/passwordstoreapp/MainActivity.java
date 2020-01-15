package com.example.passwordstoreapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
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

import com.example.passwordstoreapp.Dagger2Staff.*;
import com.example.passwordstoreapp.Database.Password;
import com.example.passwordstoreapp.EventBusStaff.DeleteEvent;
import com.example.passwordstoreapp.EventBusStaff.EditEvent;
import com.example.passwordstoreapp.Repository.DIalogUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.michaelrocks.paranoid.Obfuscate;
@Obfuscate
public class MainActivity extends AppCompatActivity {

    List<Password> passwordList;
    PasswordRecyclerAdapter adapter;
    RecyclerView recyclerView;
    Context context;
    FloatingActionButton floatActBtn;
    LinearLayoutManager layoutManager;
    MyViewModel model;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_listview);
        context=this;
        recyclerView=findViewById(R.id.recyclerView);
        layoutManager=new LinearLayoutManager(context);
        floatActBtn=findViewById(R.id.floating_action_button);
        floatActBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DIalogUtils.showInsertAlert(context,model);
            }
        });

        model= ViewModelProviders.of(this).get(MyViewModel.class);
        model.getAll().observe(this, new Observer<List<Password>>() {
            @Override
            public void onChanged(List<Password> passwords) {
                refreshData(passwords);
            }
        });
        passwordList=model.getAll().getValue();
        refreshData(passwordList);
        }

        public void refreshData(List<Password> passwords){
            passwordList=passwords;
            adapter=new PasswordRecyclerAdapter(context,passwordList);

            recyclerView.setLayoutManager(layoutManager);

            ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new SwipeToDeleteCallback(adapter));
            itemTouchHelper.attachToRecyclerView(recyclerView);


            DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,layoutManager.getOrientation());
            recyclerView.addItemDecoration(dividerItemDecoration);

            refreshList(recyclerView,adapter);
    }

    public void refreshList(RecyclerView list, RecyclerView.Adapter adapter){
        if(list==null) return;

        int bottomItem=0;
        int topItem=0;
        LinearLayoutManager linearLayoutManager=(LinearLayoutManager)list.getLayoutManager();
        if(layoutManager!=null){
            bottomItem=linearLayoutManager.findFirstVisibleItemPosition();
        }
        View v=list.getChildAt(0);
        topItem=v==null ? 0 : v.getTop();
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if(layoutManager!=null) linearLayoutManager.scrollToPosition(bottomItem);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEditEvent(EditEvent event){
        DIalogUtils.showEdittAlert(context,event.item,model);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeleteEvent(DeleteEvent event){
        Password password=passwordList.get(event.position);
        model.delete(password);

        DIalogUtils.showUndoSnackbar(recyclerView,password,model);
    }
}
