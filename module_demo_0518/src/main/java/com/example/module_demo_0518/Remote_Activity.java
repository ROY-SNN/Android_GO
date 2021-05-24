package com.example.module_demo_0518;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Remote_Activity extends AppCompatActivity {
    ArrayList<String> arrayListRemoteButtons;    // 【数据源】：利用列表存放遥控器按键名称
    private GridView gridViewRemotes;            // GridView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);

        // 数据源的准备
        getData();
        // 初始化配置器
        GridViewRemoteAdapter gridViewRemoteAdapter = new GridViewRemoteAdapter(this);
        gridViewRemotes =  (GridView) findViewById(R.id.gridViewRemotes);
        // 绑定配置器
        gridViewRemotes.setAdapter(gridViewRemoteAdapter);
        // 为GridView设置监听器
        gridViewRemotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Remote_Activity.this,"选择：" + arrayListRemoteButtons.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData(){
        arrayListRemoteButtons = new ArrayList<String>();
        for (int i = 0; i < 30; i++){
            arrayListRemoteButtons.add("key" + i);
        }
    }

    // 创建一个新的类继承BaseAdapter，并重写4个方法
    class GridViewRemoteAdapter extends BaseAdapter{
        Context context;
        LayoutInflater layoutInflater;

        public GridViewRemoteAdapter(Context context) {
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
        }

        // 适配器中对应数据的个数
        @Override
        public int getCount() {
            return 30;
        }

        // 获取数据集中与索引对应的数据项
        @Override
        public Object getItem(int position) {
            return position;
        }

        // 获取指定行对应的ID
        @Override
        public long getItemId(int position) {
            return position;
        }

        // 获取每一行Item的显示内容
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = layoutInflater.inflate(R.layout.remotes_layout, null);
            }
            TextView textViewKey;
            textViewKey = (TextView) convertView.findViewById(R.id.textViewKey);
            textViewKey.setText(arrayListRemoteButtons.get(position));
            return convertView;
        }
    }
}
