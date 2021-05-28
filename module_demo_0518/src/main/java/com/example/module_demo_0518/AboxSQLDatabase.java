package com.example.module_demo_0518;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 【AboxSQLDatabase类】：增、删、改、查...
 */
public class AboxSQLDatabase{
    SQLiteDatabase sqliteDatabase;

    public AboxSQLDatabase(SQLiteDatabase sqliteDatabase) {
        this.sqliteDatabase = sqliteDatabase;
    }

    // 【初始化】：插入30个“未学习”
    // 【ps】（仅第一次使用遥控器的时候需要初始化！）
    public void insertButton() {
        ContentValues contentValues = new ContentValues();
        for(int i = 0; i < 30; i++){
            contentValues.put(AboxCons.Field_NAME1, String.valueOf(i));
            contentValues.put(AboxCons.Field_NAME2, "未学习" + String.valueOf(i));
            sqliteDatabase.insert(AboxCons.TABLE_NAME, null, contentValues);
        }
        Log.v("数据库日志：", "第一次使用遥控器的时候，需要数据初始化(插入30个“未学习”)成功！");
    }

    // 【更新】：更改表中Key_Name（在dialog中输入的功能名）的值
    // 【ps】在学习、并测试成功后，再调用此方法完成更新！
    public void alterButton(AboxRemoteButtonBean aboxRemoteButtonBean){
        ContentValues contentValues = new ContentValues();
        contentValues.put(AboxCons.Field_NAME2, aboxRemoteButtonBean.getKeyName());

        String where = AboxCons.Field_NAME1 + "=?";
        String[] whereValues = new String[]{aboxRemoteButtonBean.getKeyId()};
        sqliteDatabase.update(AboxCons.TABLE_NAME, contentValues, where, whereValues);
        Log.v("数据库日志：", "更新的数据为" + aboxRemoteButtonBean.getKeyId() + "   " + aboxRemoteButtonBean.getKeyName());
    }

    // 【查询】：查询表中所有的信息
    public Cursor queryButtons() {
        String str = "select * from " + AboxCons.TABLE_NAME + ";";
        Cursor cursor = sqliteDatabase.rawQuery(str, null);
        Log.v("数据库日志：", "查询当前表中所有信息成功！");
        return cursor;
    }

    // 【查询】：查询表中某一个按键的信息
    public Cursor queryButton(AboxRemoteButtonBean aboxRemoteButtonBean) {
        String str = "select * from " + AboxCons.TABLE_NAME + " where "
                + AboxCons.Field_NAME1 + "='" + aboxRemoteButtonBean.getKeyId() + "';";
        Cursor cursor = sqliteDatabase.rawQuery(str, null);
        Log.v("数据库日志：", "查询当前表中某一个按键的信息！");
        return cursor;
    }

    // 【删除】：删除选中按键对应的表信息
    // 【ps】在想要删除某一个按键的时候，调用该方法。
    public void deleteButton(AboxRemoteButtonBean aboxRemoteButtonBean) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AboxCons.Field_NAME2, "未学习" + String.valueOf(aboxRemoteButtonBean.getKeyId()));

        String where = AboxCons.Field_NAME1 + "=?";
        String[] whereValues = new String[]{aboxRemoteButtonBean.getKeyId()};
        sqliteDatabase.update(AboxCons.TABLE_NAME, contentValues, where, whereValues);
        Log.v("数据库日志：", "修改(表面调用的是删除，其实是更新的思想)的数据为" + aboxRemoteButtonBean.getKeyId() + "   " + aboxRemoteButtonBean.getKeyName());
    }
}