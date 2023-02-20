package com.graduation_project.wicky.csa.db.table;

import android.database.sqlite.SQLiteDatabase;

import com.graduation_project.wicky.csa.db.DatabaseHelper;


public class Photo implements DatabaseHelper.TableCreateInterface{
    public static String tableName = "Photo";
    public static String id = "id";
    public static String title="title";
    public static String photoUrl="photoUrl";
    public static String type ="type"; //1 首页图 2 产品图

    private static Photo photo = new Photo();

    public static Photo getInstance() {
        return Photo.photo;
    }

    @Override
    public void onCreate( SQLiteDatabase db ) {

        String sql = "create table Photo(id integer primary key autoincrement,title text," +
                "photoUrl text,type integer, goodId integer)";
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
            String sql = "DROP TABLE IF EXISTS " + Photo.tableName;
            db.execSQL( sql );
            this.onCreate( db );
        }
    }

}
