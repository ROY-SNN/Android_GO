package com.example.module_demo_0518;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 【AboxSQLOpenHelper类】：管理数据库(辅助类)，创建数据库、数据表。
 */
public class AboxSQLOpenHelper extends SQLiteOpenHelper {
    private static final int version = 1;    // 数据库版本

    public AboxSQLOpenHelper(Context context) {
        super(context, AboxCons.DATABASES_NAME, null, version);
        Log.v("数据库日志：", "创建数据库完成！！！");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建数据表
        String sql = "create table " + AboxCons.TABLE_NAME +  "(" + AboxCons.Field_NAME1  + " varchar(20) not null , "
                + AboxCons.Field_NAME2 + " varchar(60) not null );";
        db.execSQL(sql);
        Log.v("数据库日志：", "数据表创建完成！！！");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
