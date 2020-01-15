package com.example.passwordstoreapp.EventBusStaff;

import com.example.passwordstoreapp.Database.Password;
import com.example.passwordstoreapp.Repository.DIalogUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EditEvent {
    public Password item;
    public int position;

    public EditEvent(Password item,int position){
        this.item=item;
        this.position=position;
    }

}
