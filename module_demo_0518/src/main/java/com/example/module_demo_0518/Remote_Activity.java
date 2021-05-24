package com.example.module_demo_0518;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

public class Remote_Activity extends AppCompatActivity {
    private GridView gridViewRemotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remotes_layout);

        gridViewRemotes =  (GridView) findViewById(R.id.gridViewRemotes);
        GridViewRemoteAdapter gridViewRemoteAdapter = new GridViewRemoteAdapter(this);
        gridViewRemotes.setAdapter(gridViewRemoteAdapter);
    }


    class GridViewRemoteAdapter extends BaseAdapter{
        Context context;
        LayoutInflater layoutInflater;

        public GridViewRemoteAdapter(Context context) {
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return 30;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = layoutInflater.inflate(R.layout.activity_remote, null);
            }
            return convertView;
        }
    }
}
