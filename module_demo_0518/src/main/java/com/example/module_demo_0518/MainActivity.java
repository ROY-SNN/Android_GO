package com.example.module_demo_0518;

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

public class MainActivity extends AppCompatActivity {
    private EditText editTextUserName;  // 【登录页面】用户名
    private EditText editTextUserPwd;   // 【登录页面】密码
    private CheckBox checkBoxRemmberMe; // 【登录页面】记住我！
    private SharedPreferences sp;       //  SharedPreferences对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();

        sp = getSharedPreferences("userinfo", MODE_PRIVATE);
        editTextUserName.setText(sp.getString(AboxCons.SP_USER_NAME, ""));
        editTextUserPwd.setText(sp.getString(AboxCons.SP_USER_PSD, ""));
    }

    /*【findView所有操作放在这里，简化代码】*/
    private void findView() {
        // 通过finView找到控件对应的对象(ps:版本问题需要强制转换)
        editTextUserName  = (EditText) findViewById(R.id.editTextUserName);
        editTextUserPwd   = (EditText) findViewById(R.id.editTextUserPwd);
        checkBoxRemmberMe = (CheckBox) findViewById(R.id.checkBoxRemmberMe);
    }

    /* 【登陆页面的“登录按钮”】
    *       当登陆按钮点击之后的事件，判断用户名与密码是否正确，
    *       如果正确：跳转到MainaActivity；如果错误：显示“登陆失败”不跳转
    * */
    public void onloginButtononCliked(View view){
        String userName = editTextUserName.getText().toString();
        String userPwd = editTextUserPwd.getText().toString();

        if(isNull(userName, userPwd)){
            Toast.makeText(this, "请填写用户名或密码", Toast.LENGTH_LONG).show();
        }else{
            LoginAsysnTask loginAsysnTask = new LoginAsysnTask(userName,userPwd);
            loginAsysnTask.execute();
        }
    }

    public boolean isNull(String Name, String Pwd){
        // 如果用户名或密码为空，直接返回false
        if(Name.equals("") || Pwd.equals("")){
            return true;
        }else{
            return false;
        }
    }

    class LoginAsysnTask extends AsyncTask<String, Void, ABRet> {
        private String m_userName;
        private String m_userPwd;

        public LoginAsysnTask(String m_userName, String m_userPwd) {
            this.m_userName = m_userName;
            this.m_userPwd = m_userPwd;
        }

        @Override
        protected ABRet doInBackground(String... userInfo) {
            ABRet abRet = ABSDK.getInstance().loginWithUsername(m_userName, m_userPwd);
            return abRet;
        }

        @Override
        protected void onPostExecute(ABRet abRet) {
            super.onPostExecute(abRet);
            if (abRet.getCode().equals("00000")){
                // 如果登录成功
                if(checkBoxRemmberMe.isChecked()){ // 且“记住我”已经勾选
                    // 3.【数据更新】
                    //   putString()方法  参数1：键；参数2：值
                    //   【ps】edit()、putString()、commit()三个方法结合使用。
                    sp.edit().putString(AboxCons.SP_USER_NAME, editTextUserName.getText().toString())
                            .putString(AboxCons.SP_USER_PSD, editTextUserPwd.getText().toString())
                            .commit();
                }
                Intent intent = new Intent(MainActivity.this, SelectiveSensorActivity.class);
                intent.putExtra("userName", editTextUserName.getText().toString());
                startActivity(intent);
            }else{
                if(abRet.getCode().equals("20004")){
                    Toast.makeText(MainActivity.this,"请输入正确的用户名与密码！", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"登陆失败(其他原因)"+abRet.getCode(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
