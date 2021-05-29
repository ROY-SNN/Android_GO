package com.example.module_demo_0518;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dhc.absdk.ABRet;
import com.dhc.absdk.ABSDK;


/**
 * 【门禁页面】：
 */
public class DoorActivity extends AppCompatActivity {
    private TextView textViewDoor;   // 【门磁页面】门的状态
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door);

        findView();
    }

    private void findView() {
        textViewDoor = (TextView)findViewById(R.id.textViewDoor);
        imageView = (ImageView) findViewById(R.id.imageViewDoor);

        // 实例化门禁对象
        // 【ps】execute()后面不要写东西
        DoorStatusTask doorStatusTask = new DoorStatusTask();
        doorStatusTask.execute();
    }

    /**
     * 【DoorStatusTask类】：门禁权限获取、状态获取
     *      【ps】需要引入jar包
     */
    class DoorStatusTask extends AsyncTask<Void, Void, ABRet> {

        @Override
        protected ABRet doInBackground(Void... Voids) {
            ABRet abRet = ABSDK.getInstance().getDoorStatus(AboxCons.DOOR_DEVICE);
            return abRet;
        }

        @Override
        protected void onPostExecute(ABRet abRet) {
            super.onPostExecute(abRet);
            if (abRet.getCode().equals("00000")){
                String door_status = abRet.getDicDatas().get("status").toString();
                if(door_status.equals("0")){
                    textViewDoor.setText("关闭");
                }else{
                    textViewDoor.setText("开启");
                }
                imageView.setImageResource(door_status.equals("0") ? R.drawable.door_off  : R.drawable.door_on);
                Toast.makeText(DoorActivity.this,"门禁状态获取成功", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(DoorActivity.this,"门禁状态获取失败" + abRet.getCode(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
