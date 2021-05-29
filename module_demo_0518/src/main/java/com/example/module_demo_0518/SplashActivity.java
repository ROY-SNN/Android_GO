package com.example.module_demo_0518;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * 【app打开前的载入页面】：进度条等等
 */
public class SplashActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private int mProgress=0;
    private Handler mHandler;

    Handler mHandler_former = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 隐藏标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        ImageView imageView = (ImageView)findViewById( R.id.imageView);
        imageView.setLeft(0);
        imageView.setTop(0);
        imageView.setRight(dm.widthPixels);
        imageView.setBottom(dm.heightPixels);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mHandler_former.postDelayed(new Runnable(){
            public void run(){
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);

        progressBar =(ProgressBar)findViewById(R.id.progressBar);
        mHandler =new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==0x111){
                    progressBar.setProgress(mProgress);
                }else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    mProgress=doWork();
                    Message m=new Message();
                    if (mProgress<100){
                        m.what=0x111;
                        mHandler.sendMessage(m);
                    }else {
                        m.what=0x110;
                        mHandler.sendMessage(m);
                        break;
                    }
                }
            }

            private int doWork(){
                mProgress += 15;
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return  mProgress;
            }
        }).start();
    }
}
