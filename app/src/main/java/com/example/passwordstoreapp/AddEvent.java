package com.example.passwordstoreapp;

public class AddEvent {
    UserPasswordDB item;
    int position;

    public AddEvent(UserPasswordDB item,int position){
        this.item=item;
        this.position=position;
    }

}
