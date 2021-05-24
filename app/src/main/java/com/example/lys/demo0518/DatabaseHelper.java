package com.example.lys.demo0518;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{
        private static final String DB_NAME = "mydata4.db"; //数据库名称
        private static final int version = 1; //数据库版本

        public DatabaseHelper(Context context) {
            super(context, DB_NAME, null, version);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.v("数据库日志：", "创建数据库.....");
            String sql = "create table HWdata(Key varchar(20) not null , Value varchar(60) not null );";
            db.execSQL(sql);
            Log.v("数据库日志：", "创建完成！！！");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
        }































//    public static final String TAG = "DatabaseHelper666";
//
//    public DatabaseHelper(Context context){
//        super(context, Constants.DATABASE_NAME, null, Constants.VERSION_CODE);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        // 创建的时候的回调
//        Log.v(TAG, "创建数据库.....");
//        // String sql = "create table "  + Constants.TABLE +  "(username varchar(20) not null , password varchar(60) not null );";
//        String sql = "create table user(username varchar(20) not null , password varchar(60) not null );";
//        db.execSQL(sql);
//        Log.v(TAG, "创建完成！");
//
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // 升级数据库的回调
//        Log.v(TAG, "升级数据库.....");
//        String sql = "alter table user add adrress varchar(20) not null;";
//        db.execSQL(sql);
//
//    }
}
