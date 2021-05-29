package com.example.module_demo_0518;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.dhc.absdk.ABRet;
import com.dhc.absdk.ABSDK;

/**
 * 【TempAndHumidityActivity类】：温湿度页面
 */
public class TempAndHumidityActivity extends AppCompatActivity {
    private TextView textViewTemp;
    private TextView textViewHumidity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_and_humidity);

        findView();
        THStatusTask thStatusTask = new THStatusTask();
        thStatusTask.execute();
    }

    private void findView(){
        textViewTemp = (TextView) findViewById(R.id.textViewTemp);
        textViewHumidity = (TextView) findViewById(R.id.textViewHum);
    }

    /**
     * 【THStatusTask类】：温湿度
     *      【ps】需要引入jar包
     */
    class THStatusTask extends AsyncTask<Void, Void, ABRet> {

        @Override
        protected ABRet doInBackground(Void... Voids) {
            ABRet abRet = ABSDK.getInstance().getThStatus(AboxCons.TEMP_HUMIDITY_DEVICE);
            return abRet;
        }

        @Override
        protected void onPostExecute(ABRet abRet) {
            super.onPostExecute(abRet);

            if (abRet.getCode().equals("00000")){
                textViewTemp.setText(abRet.getDicDatas().get("temperature").toString());
                textViewHumidity.setText(abRet.getDicDatas().get("humidity").toString());
                if(Double.parseDouble(abRet.getDicDatas().get("temperature").toString()) > 26.0){
                    Toast.makeText(TempAndHumidityActivity.this,"有一点小热",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(TempAndHumidityActivity.this,"哎呦~~不粗哦，温度很舒适！",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(TempAndHumidityActivity.this,"温湿度获取失败"+abRet.getCode(),Toast.LENGTH_SHORT).show();
            }
        }
    }
}
