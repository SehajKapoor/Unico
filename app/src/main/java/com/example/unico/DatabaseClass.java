package com.example.unico;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseClass extends SQLiteOpenHelper {
    public DatabaseClass(@Nullable Context context) {
        super(context, "MyDatabase", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table Teacher(name text,email text,phone text,gender text,incharge text,designation text,username text,password text,security text,answer text)");

        db.execSQL("create table Student(name text,email text,phone text,gender text,roll_no text,class_name text,username text,password text,security text,answer text)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addDataT(String s1, String s2, String s3, String s4, String s5, String s6, String s7, String s8, String s9, String s10) {
        SQLiteDatabase sql = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", s1);
        cv.put("email", s2);
        cv.put("phone", s3);
        cv.put("gender", s4);
        cv.put("incharge", s5);
        cv.put("designation", s6);
        cv.put("username", s7);
        cv.put("password", s8);
        cv.put("security", s9);
        cv.put("answer", s10);
        sql.insert("Teacher", null, cv);
        return true;
    }

    public String getDataT(String n) {
        SQLiteDatabase sql = getReadableDatabase();
        String query = "Select username,password from Teacher";
        Cursor cursor = sql.rawQuery(query, null);
        String a, b;
        b = "not found";
        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);
                if (a.equals(n)) {
                    b = cursor.getString(1);
                    break;
                }

            } while (cursor.moveToNext());

        }
        return b;
    }

    public boolean addDataStudent(String s1, String s2, String s3, String s4, String s5, String s6, String s7, String s8, String s9, String s10) {
        SQLiteDatabase sql = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", s1);
        cv.put("email", s2);
        cv.put("phone", s3);
        cv.put("roll_no", s4);
        cv.put("gender", s5);
        cv.put("class_name", s6);
        cv.put("username", s7);
        cv.put("password", s8);
        cv.put("security", s9);
        cv.put("answer", s10);
        sql.insert("Student", null, cv);
        return true;
    }

    public String getDataStudent(String n) {
        SQLiteDatabase sql = getReadableDatabase();
        String query = "Select username,password from Student";
        Cursor cursor = sql.rawQuery(query, null);
        String a, b;
        b = "not found";
        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);
                if (a.equals(n)) {
                    b = cursor.getString(1);
                    break;
                }

            } while (cursor.moveToNext());

        }
        return b;
    }
//    public boolean addDataY2(String s1,String s2,String s3,String s4,String s5,String s6,String s7,String s8,String s9,String s10)
//    {
//        SQLiteDatabase sql=getWritableDatabase();
//        ContentValues cv= new ContentValues();
//        cv.put("name",s1);
//        cv.put("email",s2);
//        cv.put("phone",s3);
//        cv.put("roll_no",s4);
//        cv.put("gender",s5);
//        cv.put("year",s6);
//        cv.put("username",s7);
//        cv.put("password",s8);
//        cv.put("security",s9);
//        cv.put("answer",s10);
//        sql.insert("Year2",null,cv);
//        return true;
//    }
//    public String getDataY2(String n)
//    {
//        SQLiteDatabase sql=getReadableDatabase();
//        String query="Select username,password from Year1";
//        Cursor cursor=sql.rawQuery(query,null);
//        String a,b;
//        b="not found";
//        if(cursor.moveToFirst())
//        {
//            do{
//                a=cursor.getString(0);
//                if(a.equals(n))
//                {
//                    b=cursor.getString(1);
//                    break;
//                }
//
//            }while(cursor.moveToNext());
//
//        }
//        return b;
//    }
//    public boolean addDataY3(String s1,String s2,String s3,String s4,String s5,String s6,String s7,String s8,String s9,String s10)
//    {
//        SQLiteDatabase sql=getWritableDatabase();
//        ContentValues cv= new ContentValues();
//        cv.put("name",s1);
//        cv.put("email",s2);
//        cv.put("phone",s3);
//        cv.put("roll_no",s4);
//        cv.put("gender",s5);
//        cv.put("year",s6);
//        cv.put("username",s7);
//        cv.put("password",s8);
//        cv.put("security",s9);
//        cv.put("answer",s10);
//        sql.insert("Year3",null,cv);
//        return true;
//    }
//    public String getDataY3(String n)
//    {
//        SQLiteDatabase sql=getReadableDatabase();
//        String query="Select username,password from Year1";
//        Cursor cursor=sql.rawQuery(query,null);
//        String a,b;
//        b="not found";
//        if(cursor.moveToFirst())
//        {
//            do{
//                a=cursor.getString(0);
//                if(a.equals(n))
//                {
//                    b=cursor.getString(1);
//                    break;
//                }
//
//            }while(cursor.moveToNext());
//
//        }
//        return b;
//    }
//    public boolean addDataY4(String s1,String s2,String s3,String s4,String s5,String s6,String s7,String s8,String s9,String s10)
//    {
//        SQLiteDatabase sql=getWritableDatabase();
//        ContentValues cv= new ContentValues();
//        cv.put("name",s1);
//        cv.put("email",s2);
//        cv.put("phone",s3);
//        cv.put("roll_no",s4);
//        cv.put("gender",s5);
//        cv.put("year",s6);
//        cv.put("username",s7);
//        cv.put("password",s8);
//        cv.put("security",s9);
//        cv.put("answer",s10);
//        sql.insert("Year4",null,cv);
//        return true;
//    }
//    public String getDataY4(String n)
//    {
//        SQLiteDatabase sql=getReadableDatabase();
//        String query="Select username,password from Year1";
//        Cursor cursor=sql.rawQuery(query,null);
//        String a,b;
//        b="not found";
//        if(cursor.moveToFirst())
//        {
//            do{
//                a=cursor.getString(0);
//                if(a.equals(n))
//                {
//                    b=cursor.getString(1);
//                    break;
//                }
//
//            }while(cursor.moveToNext());
//
//        }
//        return b;
//    }


    public Cursor showRegisteredData() {
        SQLiteDatabase sql = getReadableDatabase();
        Cursor c = sql.query("Teacher", null, null, null, null, null, null);
        return c;
    }


    public Cursor showRegisteredDataStudent() {
        SQLiteDatabase sql = getReadableDatabase();
        Cursor c = sql.query("Student", null, null, null, null, null, null);
        return c;
    }

    public Cursor getNameList() {
       SQLiteDatabase sql=getWritableDatabase();
        String query = "Select name,roll_no from Student";
        Cursor cursor = sql.rawQuery(query, null);
        return cursor;
    }
    }

