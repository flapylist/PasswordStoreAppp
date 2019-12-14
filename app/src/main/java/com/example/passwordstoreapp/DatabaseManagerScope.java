package com.example.passwordstoreapp;

import androidx.recyclerview.selection.ItemKeyProvider;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

@Scope
@Retention(RetentionPolicy.CLASS)
public @interface DatabaseManagerScope {
}
