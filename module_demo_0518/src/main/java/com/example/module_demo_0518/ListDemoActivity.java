package com.example.module_demo_0518;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListDemoActivity extends Activity {
    ArrayList<String> arrayListDevices;    // 【数据源】：利用列表存放设备名称
    ListView listViewDevice;               //  ListView
    ArrayAdapter<String> arrayAdpater;     //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_demo);

        getData();
        arrayAdpater = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayListDevices);
        listViewDevice = (ListView) findViewById(R.id.ListViewDevice);
        listViewDevice.setAdapter(arrayAdpater);

        // 为ListView设置监听器
        listViewDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListDemoActivity.this,"选择：" + arrayListDevices.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 【数据源】设备名称的赋值
    private void getData() {
        arrayListDevices = new ArrayList<String>();
        arrayListDevices.add(AboxCons.INFRARED_DEVICE);
        arrayListDevices.add(AboxCons.SOCKET_DEVICE);
    }
}
