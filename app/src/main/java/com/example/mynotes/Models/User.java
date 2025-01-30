package com.example.mynotes.Models;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int userId;

    private String login;
    private String password;

    // Геттеры и сеттеры
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}