package com.demo.database.sqlitedemo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.demo.database.sqlitedemo.entity.UserEntity;

import java.util.ArrayList;

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "user";
    public static int DATABASE_VERSION = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(UserEntity.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        DATABASE_VERSION = newVersion;
        sqLiteDatabase.execSQL(UserEntity.DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    public long insertUserEntity(UserEntity userEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserEntity.COLUMN_NAME, userEntity.getName());
        contentValues.put(UserEntity.COLUMN_MOBILE, userEntity.getMobile());
        contentValues.put(UserEntity.COLUMN_ADDRESS, userEntity.getAddress());
        contentValues.put(UserEntity.COLUMN_PROFILE_PIC, userEntity.getProfilePic());
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long id = sqLiteDatabase.insert(UserEntity.TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
        return id;
    }

    public ArrayList<UserEntity> getAllUsers() {
        ArrayList<UserEntity> userEntityArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "Select * from " + UserEntity.TABLE_NAME;

        try {
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String a = cursor.getString(cursor.getColumnIndex(UserEntity.COLUMN_NAME));
                    String id = cursor.getString(0);
                    String name = cursor.getString(1);
                    String mobile = cursor.getString(2);
                    String address = cursor.getString(3);
                    byte[] userProfile = cursor.getBlob(4);
                    userEntityArrayList.add(new UserEntity(Integer.parseInt(id), name, mobile, address, userProfile));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sqLiteDatabase.close();
        return userEntityArrayList;
    }


}
