package com.graduation_project.wicky.csa.db.table;

import android.database.sqlite.SQLiteDatabase;

import com.graduation_project.wicky.csa.db.DatabaseHelper;


public class Order implements DatabaseHelper.TableCreateInterface{
    public static String tableName = "Orders";
    public static String id = "id";
    public static String sum="sum";
    public static String goodTotal="goodTotal";
    public static String goodId="goodId";
    public static String amount="amount";
    public static String note="note";
    public static String orderStatus="orderStatus"; // 0 待支付  1 认养中 -1 已完成  2 待审核 3 已审核
    public static String orderNumber="orderNumber";
    public static String year="year";
    public static String supplerId="supplerId";
    public static String adopterId="adopterId";
    public static String isInsure="isInsure";
    public static String createDate="createDate";

    private static Order order = new Order();

    public static Order getInstance() {
        return Order.order;
    }

    @Override
    public void onCreate( SQLiteDatabase db ) {

        String sql = "create table Orders(id integer primary key autoincrement, sum double," +
                "goodTotal double, goodId integer, amount integer, note text, orderStatus integer, orderNumber text,"+
        "year integer, supplerId integer, adopterId integer, createDate LONG)";
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
            String sql = "DROP TABLE IF EXISTS " + Order.tableName;
            db.execSQL( sql );
            this.onCreate( db );
        }
    }

}
