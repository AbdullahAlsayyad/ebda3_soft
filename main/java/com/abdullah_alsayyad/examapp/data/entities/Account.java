package com.abdullah_alsayyad.examapp.data.entities;

import androidx.annotation.StringDef;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "accounts_table")
public class Account {
    public static final String usernameColumn = "username";
    public static final String passwordColumn = "password";
    public static final String nameColumn = "name";
    public static final String descriptionColumn = "description";
    @StringDef({usernameColumn, passwordColumn, nameColumn, descriptionColumn})
    public @interface Columns{}

    @PrimaryKey
    @NotNull
    @ColumnInfo(name = usernameColumn)
    private String username;

    @ColumnInfo(name = passwordColumn)
    private String password;

    @ColumnInfo(name = nameColumn)
    private String name;

    @ColumnInfo(name = descriptionColumn)
    private String description;

    public Account(String username, String password, String name, String description) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
