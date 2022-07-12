package com.example.youmie.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseUtils extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "UsersDB.db";

    public DatabaseUtils(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(username TEXT , password TEXT, placename TEXT, foodtype TEXT, price DECIMAL(5,2))");
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

    public Boolean checkUserData(String username, String password) {
        SQLiteDatabase liteDatabase = this.getReadableDatabase();
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

    public boolean insertHostData(String username, String placename, String foodtype, float price) {
        SQLiteDatabase liteDatabase = this.getWritableDatabase();
        String passwd = null;
        boolean isUpdateNeeded = false;

        try (Cursor cursor = liteDatabase.rawQuery("Select * from users where username = ?", new String[]{username})) {
            if (cursor.moveToFirst()) {
                passwd = cursor.getString(1);
                if (cursor.getString(2) == null || cursor.getString(3) == null || cursor.getString(4) == null) {
                    isUpdateNeeded = true;
                }
            }
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", passwd);
        contentValues.put("placename", placename);
        contentValues.put("foodtype", foodtype);
        contentValues.put("price", price);

        long result;

        if (isUpdateNeeded) {
            result = liteDatabase.update("users", contentValues, "username = ?", new String[]{username});
        } else {
            result = liteDatabase.insert("users", null, contentValues);
        }

        return result != -1;
    }

    public ArrayList<Host> readUsersTable() {
        SQLiteDatabase liteDatabase = this.getReadableDatabase();
        try (Cursor cursor = liteDatabase.rawQuery("Select * from users", null)) {
            ArrayList<Host> usersList = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    usersList.add(new Host(cursor.getString(0), cursor.getString(2), cursor.getString(3), cursor.getString(4)));
                } while (cursor.moveToNext());
            }
            cursor.close();

            return usersList;
        }
    }

    public boolean deleteUserHostEntries(String username) {
        SQLiteDatabase liteDatabase = this.getReadableDatabase();
        long result = liteDatabase.delete("users", "username = ?", new String[]{username});
        return result != -1;
    }
}
