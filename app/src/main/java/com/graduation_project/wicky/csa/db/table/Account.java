package com.graduation_project.wicky.csa.db.table;

import android.database.sqlite.SQLiteDatabase;

import com.graduation_project.wicky.csa.db.DatabaseHelper;


/**
 * Created by Lenovo on 2017/12/17.
 */
public class Account implements DatabaseHelper.TableCreateInterface{
    public static String tableName = "Account";
    public static String _id = "_id";
    public static String UID = "UID";
    public static String password = "password";
    public static String name = "Account_Name";
    public static String sex = "Account_Sex";

    private static Account account = new Account();

    public static Account getInstance() {
        return Account.account;
    }

    @Override
    public void onCreate( SQLiteDatabase db ) {

        String sql = "create table Account(_id integer primary key autoincrement,UID text," +
                "password text,name text,sex text)";
        /*String sql = "CREATE TABLE "
                + Account.tableName
                + " (  "
                + "_id integer primary key autoincrement, "
                + Account.UID + " LONG, "
                +Account.password + "TEXT"
                + Account.name + " TEXT, "
                + Account.sex + " TEXT, "
                + ");";*/
        db.execSQL( sql );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {

        if ( oldVersion < newVersion ) {
            String sql = "DROP TABLE IF EXISTS " + Account.tableName;
            db.execSQL( sql );
            this.onCreate( db );
        }
    }

}
