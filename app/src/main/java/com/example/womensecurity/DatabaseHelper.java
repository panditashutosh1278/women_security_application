package com.example.womensecurity;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydatabase.db";
    private static final String TABLE_NAME = "mytable";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PHONE + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableQuery);
        onCreate(db);
    }

    public long insertData(String name, String phone) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PHONE, phone);
        return db.insert(TABLE_NAME, null, values);
    }

    public ArrayList<String> getAllData() {
        ArrayList<String> dataList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE));
                dataList.add("Name: " + name + "\nPhone: " + phone);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dataList;
    }

    public Cursor getUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"phoneNumber"};
        Cursor cursor = db.query("users", columns, null, null, null, null, null);
        return cursor;
    }

    public ArrayList<String> getAllGuardianNumbers() {
        ArrayList<String> guardianNumbers = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("users", new String[]{"phoneNumber"}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex("phoneNumber"));
                guardianNumbers.add(number);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return guardianNumbers;
    }

    public int deleteUser(String name) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = COLUMN_NAME + "=?";
        String[] whereArgs = {name};
        return db.delete(TABLE_NAME, whereClause, whereArgs);
    }

}