package com.example.module_demo_0518;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dhc.absdk.ABRet;
import com.dhc.absdk.ABSDK;

public class SocketActivity extends AppCompatActivity {
    private TextView textViewSocket_St;
    private String SocketStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);

        findView();
    }

    private void findView(){
        textViewSocket_St = (TextView) findViewById(R.id.textViewSocket_St);
    }

    public void onClickSocket(View view) {
        SocketStatusTask socketStatusTask = new SocketStatusTask();
        socketStatusTask.execute();
    }


    class SocketStatusTask extends AsyncTask<Void, Void, ABRet> {

        @Override
        protected ABRet doInBackground(Void... Voids) {
            ABRet abRet = ABSDK.getInstance().getSockStatus("s1");
            return abRet;
        }

        @Override
        protected void onPostExecute(ABRet abRet) {
            super.onPostExecute(abRet);

            if (abRet.getCode().equals("00000")) {
                Toast.makeText(SocketActivity.this, "插座权限获取成功", Toast.LENGTH_SHORT).show();
                // 获取当前插座的状态，并打印
                SocketStatus = abRet.getDicDatas().get("status").toString();
                textViewSocket_St.setText(SocketStatus);
                // 创建控制插座的实例对象，并将当前状态赋值给它
                ConrolSocketStatusTask conrolSocketStatusTask = new ConrolSocketStatusTask(SocketStatus);
                conrolSocketStatusTask.execute();
            } else {
                Toast.makeText(SocketActivity.this, "插座权限获取失败" + abRet.getCode(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    class ConrolSocketStatusTask extends AsyncTask<String, Void, ABRet> {
        private String SocketStatus1;

        public ConrolSocketStatusTask(String SocketStatus) {
            SocketStatus1 = SocketStatus;
        }

        @Override
        protected ABRet doInBackground(String... Voids) {
            if (SocketStatus1.equals("0")) {
                SocketStatus1 = "1";
            } else {
                SocketStatus1 = "0";
            }
            ABRet abRet = ABSDK.getInstance().sockCtrl("s1", SocketStatus1);
            return abRet;
        }

        @Override
        protected void onPostExecute(ABRet abRet) {
            super.onPostExecute(abRet);

            if (abRet.getCode().equals("00000")) {
                Toast.makeText(SocketActivity.this, "插座状态“更改”为：" + SocketStatus1, Toast.LENGTH_SHORT).show();
                textViewSocket_St.setText(SocketStatus1);
            } else {
                Toast.makeText(SocketActivity.this, "插座状态“更改”失败！！！！" + abRet.getCode(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
