package com.example.module_demo_0518;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;


/**
 * 【ColoredEggsActivity类】：主页面的侧滑菜单中“关于我们”的页面
 */
public class ColoredEggsActivity extends AppCompatActivity {
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draww);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.abcdef);
        final AnimationDrawable anim = (AnimationDrawable) relativeLayout.getBackground();
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag){
                    anim.start();
                    flag = false;
                }else{
                    anim.stop();
                    flag=true;
                }

            }
        });
    }
}
