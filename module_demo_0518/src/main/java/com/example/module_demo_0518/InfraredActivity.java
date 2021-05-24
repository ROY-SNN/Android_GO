package com.example.module_demo_0518;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.dhc.absdk.ABRet;
import com.dhc.absdk.ABSDK;

public class InfraredActivity extends AppCompatActivity {
    private EditText editTextEnterKey;
//    private DatabaseHelper database = new DatabaseHelper(InfraredActivity.this);
//    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infrared);

        editTextEnterKey = (EditText) findViewById(R.id.editTextEnterKey);
    }

    public void onClickLearn(View view){
        InfraredLearnTask infraredLearnTask = new InfraredLearnTask(editTextEnterKey.getText().toString());
        infraredLearnTask.execute();
    }

    public void onClickTest(View view){
        InfraredTestTask infraredTestTask = new InfraredTestTask(editTextEnterKey.getText().toString());
        infraredTestTask.execute();
    }

    class InfraredLearnTask extends AsyncTask<String, Void, ABRet> {
        private String str_Key;

        public InfraredLearnTask(String str_Key){
            this.str_Key = str_Key;
        }

        @Override
        protected ABRet doInBackground(String... Voids) {
            ABRet abRet = ABSDK.getInstance().studyIrByIrDevName(AboxCons.INFRARED_DEVICE, str_Key);
            return abRet;
        }

        @Override
        protected void onPostExecute(ABRet abRet) {
            super.onPostExecute(abRet);

            if (abRet.getCode().equals("00000")) {
                Toast.makeText(InfraredActivity.this,"红外学习成功", Toast.LENGTH_SHORT).show();
//                // ========================存入数据库=============================
//                db = database.getWritableDatabase();
//                abRet.getDicDatas().get("code").toString();
//                Log.v("数据日志：", "数据插入成功！");
//                // ===============================================================

            } else {
                Toast.makeText(InfraredActivity.this,"红外学习失败" + AboxCons.errorMap.get(abRet.getCode()), Toast.LENGTH_SHORT).show();
            }
        }
    }

    class InfraredTestTask extends AsyncTask<String, Void, ABRet> {
        private String str_Key;

        public InfraredTestTask(String str_Key){
            this.str_Key = str_Key;
        }

        @Override
        protected ABRet doInBackground(String... Voids) {
            ABRet abRet = ABSDK.getInstance().sendIr(AboxCons.INFRARED_DEVICE, str_Key);
            return abRet;
        }

        @Override
        protected void onPostExecute(ABRet abRet) {
            super.onPostExecute(abRet);

            if (abRet.getCode().equals("00000")) {
                Toast.makeText(InfraredActivity.this,"红外发射成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(InfraredActivity.this,"红外发射失败" + AboxCons.errorMap.get(abRet.getCode()) , Toast.LENGTH_SHORT).show();
            }
        }
    }

}
