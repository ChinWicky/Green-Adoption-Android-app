package com.graduation_project.wicky.csa.db.table;

import android.database.sqlite.SQLiteDatabase;

import com.graduation_project.wicky.csa.db.DatabaseHelper;


public class Pay implements DatabaseHelper.TableCreateInterface{
    public static String tableName = "Pay";
    public static String id = "id";
    public static String amount = "amount";
    public static String payerId ="payerId";
    public static String payeeId="payeeId";
    public static String createDate="createDate";
    public static String orderId="orderId";
    public static String orderNumber="orderNumber";

    private static Pay Pay = new Pay();

    public static Pay getInstance() {
        return Pay.Pay;
    }

    @Override
    public void onCreate( SQLiteDatabase db ) {

//        String sql = "create table Pay(id integer primary key autoincrement,title text," +
//                "photoUrl text,type integer, goodId integer)";
        String sql = "CREATE TABLE "
                + Pay.tableName
                + "("
                + "id integer primary key autoincrement, "
                + Pay.amount + " double,"
                + Pay.payerId + " integer,"
                + Pay.payeeId + " integer,"
                + Pay.createDate + " long,"
                + Pay.orderId + " integer,"
                + Pay.orderNumber + " text"
                + ");";
        db.execSQL( sql );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        if ( oldVersion < newVersion ) {
            String sql = "DROP TABLE IF EXISTS " + Pay.tableName;
            db.execSQL( sql );
            this.onCreate( db );
        }
    }

}
