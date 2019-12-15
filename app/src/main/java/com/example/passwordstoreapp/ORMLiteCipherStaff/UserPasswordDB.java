package com.example.passwordstoreapp.ORMLiteCipherStaff;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "table_name")
public class UserPasswordDB {

    @DatabaseField(generatedId = true)
    private Long id;

    public Long getId() {
        return id;
    }

    @DatabaseField
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DatabaseField
    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @DatabaseField
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserPasswordDB(){
    }
}
