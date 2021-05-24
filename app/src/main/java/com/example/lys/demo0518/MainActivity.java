package com.example.lys.demo0518;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ======================1.初始化======================
        DatabaseHelper database = new DatabaseHelper(this);
        db = database.getWritableDatabase();
        // ContentValues cv = new ContentValues();

        // ======================2.插入数据(2种方法)======================
//        // 【方法一】：利用ContentValues.put方法与DatabaseHelper.insert方法
//        cv.put("Key","1");
//        cv.put("Value","红外代码指令1"); //添加密码
//        db.insert("HWdata",null,cv);//执行插入操作

        // 【方法二】：利用sql语句与execSQL()方法
        String sql;
        for(int i = 0; i < 15; i++){
            sql = "insert into HWdata(Key,Value) values (" + "'" +  String.valueOf(i) + "'" +  ",'红外代码指令" + String.valueOf(i) + "')";
            db.execSQL(sql);
        }
        Log.v("数据库日志：", "数据插入成功！");

        // ======================3.查找数据(2种：但是query()方法有问题！！！)======================
        Cursor cursor = db.rawQuery("select * from HWdata", null);
        while (cursor.moveToNext()) {
            String key = cursor.getString(0); //获取第一列的值,第一列的索引从0开始
            String value = cursor.getString(1); //获取第一列的值,第一列的索引从0开始
            Log.v("数据库日志：", key + " " + value);
        }
        // =======================
        String input_key = "12";
        cursor = db.rawQuery("select * from HWdata where Key='" + input_key + "'",null);
        //Cursor cursor = db.rawQuery("select * from HWdata where Key='34'",null);
        if(cursor.moveToFirst()) {
            String value = cursor.getString(cursor.getColumnIndex("Value"));
            Log.v("数据库日志：", " Key为12对应的value为 " + value);
        }












//        // ======================4.删除数据（没调试好！！！！有问题）======================
//        String input_key = "4";
//        Cursor cursor1 = db.rawQuery("delete from HWdata WHERE Key='" + input_key + "'",null);
//        Log.v("数据库日志：", "删除完成");
//
//        Cursor cursor2 = db.rawQuery("select * from HWdata", null);
//        while (cursor2.moveToNext()) {
//            String key = cursor2.getString(0); //获取第一列的值,第一列的索引从0开始
//            String value = cursor2.getString(1); //获取第一列的值,第一列的索引从0开始
//            Log.v("数据库日志：", key + " " + value);
//        }



//        // 【ps】只能查询一部分结果！！！！不知道为什么？？？？
//        Cursor c = db.query("HWdata",null,null,null,null,null,null);
//        if(c.moveToFirst()){
//            for(int i=0; i<c.getCount(); i++){
//                c.move(i);
//                String Key = c.getString(c.getColumnIndex("Key"));
//                String Value = c.getString(c.getColumnIndex("Value"));
//                Log.v("数据库日志：", Key + " " + Value);
//            }
//        }else{
//            Log.v("数据库日志：", "表中无数据！");
//        }
//        Log.v("数据库日志：", "数据查询结束！！！");
    }
}
