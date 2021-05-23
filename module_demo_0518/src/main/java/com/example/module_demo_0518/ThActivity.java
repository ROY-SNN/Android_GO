package com.example.module_demo_0518;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.dhc.absdk.ABRet;
import com.dhc.absdk.ABSDK;

public class ThActivity extends AppCompatActivity {
    private TextView textViewTemp;
    private TextView textViewHumidity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_th);


        findView();
        // 获取温度和湿度的数值，并显示到相应的控件上

        THStatusTask thStatusTask = new THStatusTask();
        thStatusTask.execute();

    }

    private void findView() {
        textViewTemp = (TextView)findViewById(R.id.textViewTemp);
        textViewHumidity = (TextView)findViewById(R.id.textViewHumidity);
    }

    class THStatusTask extends AsyncTask<Void, Void, ABRet> {

        @Override
        protected ABRet doInBackground(Void... Voids) {
            ABRet abRet = ABSDK.getInstance().getThStatus("TH1");
            return abRet;
        }

        @Override
        protected void onPostExecute(ABRet abRet) {
            super.onPostExecute(abRet);

            if (abRet.getCode().equals("00000")){
                Toast.makeText(ThActivity.this,"获取成功", Toast.LENGTH_SHORT).show();
                String temp = abRet.getDicDatas().get("temperature").toString();
                String humidity = abRet.getDicDatas().get("temperature").toString();

                textViewTemp.setText(temp);
                textViewHumidity.setText(humidity);

            }else{
                Toast.makeText(ThActivity.this,"失败"+abRet.getCode(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
