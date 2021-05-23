package com.example.module_demo_0518;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dhc.absdk.ABRet;
import com.dhc.absdk.ABSDK;

public class Main2Activity extends AppCompatActivity {

    private TextView textViewshowusername; // 【BMI计算页面】用户名(用来承接上一页面的用户名)
    private EditText editTextHeight;       // 【BMI计算页面】身高
    private EditText editTextWeight;       // 【BMI计算页面】体重
    private TextView textViewTemp;
    private TextView textViewHumidity;
    private String SocketStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findView();

        THStatusTask thStatusTask = new THStatusTask();
        thStatusTask.execute();

    }

    /*【findView所有操作放在这里，简化代码】*/
    private void findView() {
        textViewshowusername = (TextView)findViewById(R.id.textViewshowusername);
        // 获取【登陆页面】中的username：利用getIntent()、setText()
        // 【ps】：注意下面getStringExtra()中的参数与【登陆页面】的putExtra()中的“useName”
        Intent intent = getIntent();
        textViewshowusername.setText(intent.getStringExtra("useName"));

        // 获取【BMI页面】的两个editText中的文本信息
        editTextHeight  = (EditText) findViewById(R.id.editTextHeight);
        editTextWeight  = (EditText) findViewById(R.id.editTextWeight);

        // 获取【BMI页面】的温湿度中的文本信息
        textViewTemp = (TextView)findViewById(R.id.textViewTemp);
        textViewHumidity = (TextView)findViewById(R.id.textViewHumidity);
    }

    /*【BMI计算按键】*/
    public void BMIButtononCliked(View view){
//        String str1;
//        int a = Integer.valueOf(editTextHeight.getText().toString()).intValue();
//        int b = Integer.valueOf(editTextWeight.getText().toString()).intValue();
//        str1 = calculateBMI(a,b);
//        Toast.makeText(this, str1, Toast.LENGTH_LONG).show();
    }

    public void ChaZuoButtononCliked(View view){
        SocketStatusTask socketStatusTask = new SocketStatusTask();
        socketStatusTask.execute();
        //注意excute之后不能有语句
    }

//    /*【计算BMI值】*/
//    public String calculateBMI(float height, float weight){
//        String str;
//        height = height / 100;
//        float BMI = weight / (height * height);
//        if(BMI > 30 || BMI < 15){
//            str = "病态，看医生！！！！";
//        }else if(BMI <= 30 && BMI > 25){
//            str = "偏胖：少吃！！！！";
//        }else if(BMI <= 25 && BMI > 20){
//            str = "正常：你真棒！！！";
//        }else{
//            str = "偏瘦：你赶紧多吃点！！！";
//        }
//        return str;
//    }

    // ========================================插座===================================================
    class SocketStatusTask extends AsyncTask<Void, Void, ABRet> {

        @Override
        protected ABRet doInBackground(Void... Voids) {
            ABRet abRet = ABSDK.getInstance().getSockStatus("s1");
            return abRet;
        }

        @Override
        protected void onPostExecute(ABRet abRet) {
            super.onPostExecute(abRet);
            if (abRet.getCode().equals("00000")){
                Toast.makeText(Main2Activity.this,"插座权限获取成功", Toast.LENGTH_SHORT).show();

                // 获取当前插座的状态，并打印
                String SocketStatus = abRet.getDicDatas().get("status").toString();
                Toast.makeText(Main2Activity.this, "插座当前状态为：" + SocketStatus , Toast.LENGTH_SHORT).show();
                Log.v("SocketStatus", SocketStatus);
                // 创建控制插座的实例对象，并将当前状态赋值给它
                ConrolSocketStatusTask conrolSocketStatusTask = new ConrolSocketStatusTask(SocketStatus);
                conrolSocketStatusTask.execute();

            }else{
                Toast.makeText(Main2Activity.this,"插座权限获取失败"+abRet.getCode(),
                        Toast.LENGTH_SHORT).show();
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
            Log.v("SocketStatus1_before", SocketStatus1);
            if(SocketStatus1.equals("0")){
                SocketStatus1 = "1";
            }else{
                SocketStatus1 = "0";
            }
            Log.v("SocketStatus1_after", SocketStatus1);
           ABRet abRet = ABSDK.getInstance().sockCtrl("s1", SocketStatus1);
            return abRet;
        }

        @Override
        protected void onPostExecute(ABRet abRet) {
            super.onPostExecute(abRet);


            if (abRet.getCode().equals("00000")){
                Toast.makeText(Main2Activity.this, "插座状态“更改”为：" + SocketStatus1 , Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(Main2Activity.this, "插座状态“更改”失败！！！！" + abRet.getCode() , Toast.LENGTH_SHORT).show();
            }
        }

    }







    // ======================================w温湿度=====================================================
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
                Toast.makeText(Main2Activity.this,"获取成功", Toast.LENGTH_SHORT).show();
                String temp = abRet.getDicDatas().get("temperature").toString();
                String humidity = abRet.getDicDatas().get("humidity").toString();

                textViewTemp.setText(temp);
                textViewHumidity.setText(humidity);

            }else{
                Toast.makeText(Main2Activity.this,"失败"+abRet.getCode(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
