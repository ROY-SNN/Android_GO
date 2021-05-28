package com.example.module_demo_0518;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import Snack.MainSnackActivity;

/**
 * 【SelectiveSensorActivity类】：设备选择主菜单
 */
public class SelectiveSensorActivity extends AppCompatActivity {
    //private TextView textViewUserName;  // 存放登陆页面获取的用户名
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listview);

        //适配器
        String[] data = new String[]{"家长模式","儿童模式","娱乐模式","其他模式"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),R.layout.item_text,data);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Toast.makeText(SelectiveSensorActivity.this, "你点击的item是第" + position + "个", Toast.LENGTH_SHORT).show();
                 if(position == 2){
                     Intent intent = new Intent(SelectiveSensorActivity.this, MainSnackActivity.class);
                     startActivity(intent);
                 }
             }
         });



        //textViewUserName = (TextView) findViewById(R.id.textViewUserName);
        //Intent intent = getIntent();
        //textViewUserName.setText(intent.getStringExtra("userName"));
    }

    /**
     * 【按键onClickTempAndHumidity监听】：跳转温湿度页面
     */
    public void onClickTempAndHumidity(View view){
        Intent intent = new Intent(SelectiveSensorActivity.this, TempAndHumidityActivity.class);
        startActivity(intent);
    }

    /**
     * 【按键onClickSocket监听】：跳转插座页面
     */
    public void onClickSocket(View view){
        Intent intent = new Intent(SelectiveSensorActivity.this, Socket_many_Activity.class);
        startActivity(intent);
    }

    /**
     * 【按键onClickSocket监听】：跳转插座页面
     */
    public void onClickDoor(View view){
        Intent intent = new Intent(SelectiveSensorActivity.this, DoorActivity.class);
        startActivity(intent);
    }

    /**
     * 【按键onClickInfrared监听】：跳转红外遥控页面
     */
    public void onClickInfrared(View view){
        Intent intent = new Intent(SelectiveSensorActivity.this, Remote_Activity.class);
        startActivity(intent);
    }

    /**
     * 【按键onClickbuttonAbout监听】：跳转菜单
     */
    public void onClickbuttonAbout(View view){
        Intent intent = new Intent(SelectiveSensorActivity.this, ColoredEggsActivity.class);
        startActivity(intent);
    }

}
