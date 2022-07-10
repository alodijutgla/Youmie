package com.example.youmie.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseUtils extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "UsersDB.db";

    public DatabaseUtils(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(username TEXT primary key, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
    }

    public boolean verifyUsername(String username) {
        SQLiteDatabase liteDatabase = this.getReadableDatabase();
        try (Cursor cursor = liteDatabase.rawQuery("Select * from users where username = ?", new String[]{username})) {
            return cursor.getCount() > 0;
        }
    }

    public boolean checkIfNonUniquePwd(String pwd) {
        SQLiteDatabase liteDatabase = this.getReadableDatabase();
        try (Cursor cursor = liteDatabase.rawQuery("Select * from users where password = ?", new String[]{pwd})) {
            return cursor.getCount() > 0;
        }
    }

    public Boolean checkUserData(String username, String password){
        SQLiteDatabase liteDatabase = this.getWritableDatabase();
        try (Cursor cursor = liteDatabase.rawQuery("Select * from users where username = ? and password = ?", new String[]{username, password})) {
            return cursor.getCount() > 0;
        }
    }

    public boolean insertUser(String username, String password) {
        SQLiteDatabase liteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = liteDatabase.insert("users", null, contentValues);
        return result != -1;
    }

}
