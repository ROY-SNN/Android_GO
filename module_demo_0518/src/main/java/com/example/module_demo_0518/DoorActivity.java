package com.example.module_demo_0518;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.dhc.absdk.ABRet;
import com.dhc.absdk.ABSDK;

public class DoorActivity extends AppCompatActivity {
    private TextView textViewDoor;   // 【门磁页面】门的状态


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door);

        findView();
    }

    private void findView() {
        textViewDoor = (TextView)findViewById(R.id.textViewDoor);

        DoorStatusTask doorStatusTask = new DoorStatusTask();
        doorStatusTask.execute();
    }

    class DoorStatusTask extends AsyncTask<Void, Void, ABRet> {
        @Override
        protected ABRet doInBackground(Void... Voids) {
            ABRet abRet = ABSDK.getInstance().getDoorStatus("door");
            return abRet;
        }

        @Override
        protected void onPostExecute(ABRet abRet) {
            super.onPostExecute(abRet);
            if (abRet.getCode().equals("00000")){
                String door_status = abRet.getDicDatas().get("status").toString();
                textViewDoor.setText(door_status);
                Toast.makeText(DoorActivity.this,"门禁状态获取成功", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(DoorActivity.this,"门禁状态获取失败" + abRet.getCode(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }









}
