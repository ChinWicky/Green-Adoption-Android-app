package com.graduation_project.wicky.csa.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.graduation_project.wicky.csa.db.table.Account;
import com.graduation_project.wicky.csa.db.table.Good;
import com.graduation_project.wicky.csa.db.table.Order;
import com.graduation_project.wicky.csa.db.table.Pay;
import com.graduation_project.wicky.csa.db.table.Photo;


/**
 * Created by Lenovo on 2017/12/17.
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    private static final int VERSION = 1;
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    public interface TableCreateInterface{
        void onCreate(SQLiteDatabase db);
        void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        Photo.getInstance().onCreate(db);
        Order.getInstance().onCreate(db);
        Good.getInstance().onCreate(db);
        Pay.getInstance().onCreate(db);
//        Topic.getInstance().onCreate(db);
//        Letter.getInstance().onCreate(db);
//        Friend.getInstance().onCreate(db);
//        Discuss.getInstance().onCreate(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Photo.getInstance().onUpgrade(db, oldVersion, newVersion);
        Order.getInstance().onUpgrade(db, oldVersion, newVersion);
        Good.getInstance().onUpgrade(db, oldVersion, newVersion);
        Pay.getInstance().onUpgrade(db, oldVersion, newVersion);
//        Topic.getInstance().onUpgrade(db, oldVersion, newVersion);
//        Letter.getInstance().onUpgrade(db, oldVersion, newVersion);
//        Friend.getInstance().onUpgrade(db, oldVersion, newVersion);
//        Discuss.getInstance().onUpgrade(db, oldVersion, newVersion);
        //City.getInstance().onUpgrade(db, oldVersion, newVersion);
    }
}
