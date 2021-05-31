package com.example.module_demo_0518;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class ConnectUsActivity extends AppCompatActivity {
    private TextView textView_github;
    private TextView textView_blog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_us);

        textView_github = (TextView) findViewById(R.id.textView_github);
        textView_blog = (TextView) findViewById(R.id.textView_blog);
        textView_github.setMovementMethod(LinkMovementMethod.getInstance());
        textView_blog.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
