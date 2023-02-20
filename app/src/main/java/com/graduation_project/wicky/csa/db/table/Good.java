package com.graduation_project.wicky.csa.db.table;

import android.database.sqlite.SQLiteDatabase;

import com.graduation_project.wicky.csa.db.DatabaseHelper;


public class Good implements DatabaseHelper.TableCreateInterface{
    public static String tableName = "Goods";
    public static String id = "id";
    public static String name="name";
    public static String price="price";
    public static String category="category";
    public static String supplerId="supplerId";
    public static String inventory="inventory";
    public static String placeId="placeId";
    public static String description="description";


    private static Good order = new Good();

    public static Good getInstance() {
        return Good.order;
    }

    @Override
    public void onCreate( SQLiteDatabase db ) {
        String sql = "CREATE TABLE "
                + Good.tableName
                + " (  "
                + "id integer primary key autoincrement, "
                + Good.name + " text,"
                + Good.price + " double,"
                + Good.category + " integer, "
                + Good.supplerId + " integer, "
                + Good.inventory + " integer, "
                + Good.placeId + " text,"
                + Good.description + " text"
                + ");";
        db.execSQL( sql );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        if ( oldVersion < newVersion ) {
            String sql = "DROP TABLE IF EXISTS " + Good.tableName;
            db.execSQL( sql );
            this.onCreate( db );
        }
    }

}
