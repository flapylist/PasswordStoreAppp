package com.example.passwordstoreapp.EventBusStaff;

import com.example.passwordstoreapp.ORMLiteCipherStaff.UserPasswordDB;

public class AddEvent {
    public UserPasswordDB item;
    public int position;

    public AddEvent(UserPasswordDB item,int position){
        this.item=item;
        this.position=position;
    }

}
