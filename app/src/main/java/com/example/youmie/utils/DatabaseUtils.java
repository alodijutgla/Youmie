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

    /**
     * Constructor of the DatabaseUtils class
     */
    public DatabaseUtils(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /**
     * onCreate() creates the table users with all the parameters
     */
    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(username TEXT , password TEXT, placename TEXT, foodtype TEXT, price DECIMAL(5,2), description TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
    }

    /**
     * verifyUsername() verifies the username is present in the table of the DB
     */
    public boolean verifyUsername(String username) {
        SQLiteDatabase liteDatabase = this.getReadableDatabase();
        try (Cursor cursor = liteDatabase.rawQuery("Select * from users where username = ?", new String[]{username})) {
            return cursor.getCount() > 0;
        }
    }

    /**
     * checkIfNonUniquePwd() verifies the password is present in the table of the DB
     */
    public boolean checkIfNonUniquePwd(String pwd) {
        SQLiteDatabase liteDatabase = this.getReadableDatabase();
        try (Cursor cursor = liteDatabase.rawQuery("Select * from users where password = ?", new String[]{pwd})) {
            return cursor.getCount() > 0;
        }
    }

    /**
     * checkUserData() verifies the username and password are present in the table of the DB
     */
    public Boolean checkUserData(String username, String password) {
        SQLiteDatabase liteDatabase = this.getReadableDatabase();
        try (Cursor cursor = liteDatabase.rawQuery("Select * from users where username = ? and password = ?", new String[]{username, password})) {
            return cursor.getCount() > 0;
        }
    }


    /**
     * insertUser() adds a user with the username and password to the users table of the DB
     */
    public boolean insertUser(String username, String password) {
        SQLiteDatabase liteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = liteDatabase.insert("users", null, contentValues);
        return result != -1;
    }

    /**
     * insertHostData() adds all the host data corresponding to the user in the users table of the DB
     */
    public boolean insertHostData(String username, String placename, String foodtype, float price, String description) {
        SQLiteDatabase liteDatabase = this.getWritableDatabase();
        String passwd = null;
        boolean isUpdateNeeded = false;

        // if just the username and password are in the table but no extra data is present we flag the isUpdateNeeded
        try (Cursor cursor = liteDatabase.rawQuery("Select * from users where username = ?", new String[]{username})) {
            if (cursor.moveToFirst()) {
                passwd = cursor.getString(1);
                if (cursor.getString(2) == null || cursor.getString(3) == null || cursor.getString(4) == null) {
                    isUpdateNeeded = true;
                }
            }
        }

        // setting all the values to the right columns
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", passwd);
        contentValues.put("placename", placename);
        contentValues.put("foodtype", foodtype);
        contentValues.put("price", price);
        contentValues.put("description", description);

        long result;

        if (isUpdateNeeded) {
            // if just the username and password are in the table but no extra data is present we update the user data
            result = liteDatabase.update("users", contentValues, "username = ?", new String[]{username});
        } else {
            // if the user is already filled in, a new entry is created for the same user
            result = liteDatabase.insert("users", null, contentValues);
        }

        return result != -1;
    }

    /**
     * readUsersTable() reads all the data from the database and returns a list of Users with all the information
     */
    public ArrayList<Host> readUsersTable() {
        SQLiteDatabase liteDatabase = this.getReadableDatabase();
        try (Cursor cursor = liteDatabase.rawQuery("Select * from users", null)) {
            ArrayList<Host> usersList = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    usersList.add(new Host(cursor.getString(0), cursor.getString(2),
                            cursor.getString(3), cursor.getString(4), cursor.getString(5)));
                } while (cursor.moveToNext());
            }
            cursor.close();

            return usersList;
        }
    }

    /**
     * deleteUserHostEntries() deletes all entries present in the table for the selected username
     */
    public boolean deleteUserHostEntries(String username) {
        SQLiteDatabase liteDatabase = this.getReadableDatabase();
        long result = liteDatabase.delete("users", "username = ?", new String[]{username});
        return result != -1;
    }
}
