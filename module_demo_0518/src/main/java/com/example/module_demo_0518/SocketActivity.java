package com.example.module_demo_0518;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dhc.absdk.ABRet;
import com.dhc.absdk.ABSDK;

public class SocketActivity extends AppCompatActivity {
    private String stateSocket;       //插座状态
    //    private String strStateSocket;
    private TextView textViewSocketState;
    private ImageView imageViewSocket;
    int i=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);

        textViewSocketState = (TextView) findViewById(R.id.textViewStateInSocket);
        imageViewSocket = (ImageView) findViewById(R.id.imageViewStateInSocket);

        CatchSocketState catchSocketState = new CatchSocketState();
        catchSocketState.execute();
    }

    public void buttonSocketSwitchOnClicked(View view){
        CatchSocketState catchSocketState = new CatchSocketState();
        catchSocketState.execute();
    }

    //*****************************************插座状态获取*****************************************//

    class CatchSocketState extends AsyncTask<Void,Void,ABRet> {
        @Override
        protected ABRet doInBackground(Void... Voids){
            ABRet abret= ABSDK.getInstance().getSockStatus("s1");
            stateSocket=abret.getDicDatas().get("status").toString();
            return abret;
        }
        @Override
        protected void onPostExecute(ABRet abret){
            super.onPostExecute(abret);
            if(abret.getCode().equals("00000")){
                textViewSocketState.setText("插座已经" + (stateSocket.equals("1") ? "开启" : "关闭"));
                imageViewSocket.setImageResource(stateSocket.equals("1") ? R.drawable.suozi  : R.drawable.suo);
                SocketControl socketControl=new SocketControl();
                socketControl.execute();
            }else{
                textViewSocketState.setText("控制插座权限获取失败");
            }
        }
    }

    //***************************************控制插座********************************************//

    class SocketControl extends AsyncTask<Void,Void,ABRet>{

        @Override
        protected ABRet doInBackground(Void...Voids){
            ABRet abret=ABSDK.getInstance().sockCtrl("s1",stateSocket.equals("1") ? "0" : "1");
            return abret;
        }
        @Override
        protected void onPostExecute(ABRet abret){
            super.onPostExecute(abret);
            if(abret.getCode().equals("00000")) {
                Toast.makeText(SocketActivity.this, "控制开关成功", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(SocketActivity.this, "控制插座失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
