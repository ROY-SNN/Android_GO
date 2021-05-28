package com.example.module_demo_0518;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.dhc.absdk.ABRet;
import com.dhc.absdk.ABSDK;

/**
 * 【登录页面】：实现用户登录的操作
 */
public class LoginActivity extends AppCompatActivity {
    private EditText editTextUserName;    // 文本输入：用户名
    private EditText editTextUserPwd;     // 文本输入：密码
    private CheckBox checkBoxRemmberMe;   // 复选框：记住密码
    private SharedPreferences sp;         //  用来存放记住密码的
    private ProgressDialog progressDialog;// 加载转圈圈

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findView();

        sp = getSharedPreferences("userinfo", MODE_PRIVATE);
        editTextUserName.setText(sp.getString(AboxCons.SP_USER_NAME, ""));
        editTextUserPwd.setText(sp.getString(AboxCons.SP_USER_PSD, ""));
    }

    /**
     * 【findView方法】：findView所有操作放在这里，简化代码。
     *      【ps】通过finViewById方法找到控件对应的对象(版本问题需要强制转换)
     */
    private void findView() {
        editTextUserName  = (EditText) findViewById(R.id.editTextUserName);
        editTextUserPwd   = (EditText) findViewById(R.id.editTextUserPwd);
        checkBoxRemmberMe = (CheckBox) findViewById(R.id.checkBoxRemmberMe);
    }

    /**
     * 【按键onloginButtononCliked监听】：
     *
     *      当登陆按钮点击之后的事件，判断用户名与密码是否正确：
     *          如果正确：跳转到SelectiveSensorActivity设备选择菜单；
     *          如果错误：
     *              如果用户名或密码未填写，打印“请填写用户名或密码”；
     *              如果用户名密码错误，打印“登陆失败”
     */
    public void onloginButtononCliked(View view){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("加载中.......");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        String userName = editTextUserName.getText().toString();
        String userPwd = editTextUserPwd.getText().toString();

        if(isNull(userName, userPwd)){
            Toast.makeText(this, "请填写用户名或密码", Toast.LENGTH_LONG).show();
        }else{
            LoginAsysnTask loginAsysnTask = new LoginAsysnTask(userName,userPwd);
            loginAsysnTask.execute();
        }
    }

    /**
     * 【isNull方法】：判断用户名或密码是否为空？
     */
    public boolean isNull(String Name, String Pwd){
        if(Name.equals("") || Pwd.equals("")){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 【LoginAsysnTask类】：判断用户是否登录成功的类，继承AsyncTask类
     *      【ps】需要官方的jar包
     */
    class LoginAsysnTask extends AsyncTask<String, Void, ABRet> {
        private String m_userName;
        private String m_userPwd;

        public LoginAsysnTask(String m_userName, String m_userPwd) {
            this.m_userName = m_userName;
            this.m_userPwd = m_userPwd;
        }

        /**
         * 【doInBackground方法】：后台执行的程序
         */
        @Override
        protected ABRet doInBackground(String... userInfo) {
            ABRet abRet = ABSDK.getInstance().loginWithUsername(m_userName, m_userPwd);
            return abRet;
        }

        /**
         * 【onPostExecute方法】：前台执行的程序
         */
        @Override
        protected void onPostExecute(ABRet abRet) {
            super.onPostExecute(abRet);

            progressDialog.dismiss();
            if (abRet.getCode().equals("00000")){// 如果登录成功
                if(checkBoxRemmberMe.isChecked()){// 且“记住密码”勾选，将用户名与密码存入文件
                    sp.edit().putString(AboxCons.SP_USER_NAME, editTextUserName.getText().toString())
                            .putString(AboxCons.SP_USER_PSD, editTextUserPwd.getText().toString())
                            .commit();
                }
                // 利用Intent进行页面跳转
                Intent intent = new Intent(LoginActivity.this, SelectiveSensorActivity.class);
                //intent.putExtra("userName", editTextUserName.getText().toString());
                startActivity(intent);
            }else{
                if(abRet.getCode().equals("20004")){
                    Toast.makeText(LoginActivity.this,"请输入正确的用户名与密码！", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this,"登陆失败"+ AboxCons.errorMap.get(abRet.getCode()), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
