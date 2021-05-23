package com.example.module_demo_0518;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SelectiveSensorActivity extends AppCompatActivity {
    private TextView textViewUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selective_sensor);

        textViewUserName = (TextView) findViewById(R.id.textViewUserName);
        Intent intent = getIntent();
        textViewUserName.setText(intent.getStringExtra("userName"));
    }

    public void onClickTempAndHumidity(View view){
        Intent intent = new Intent(SelectiveSensorActivity.this, TempAndHumidityActivity.class);
        startActivity(intent);
    }

    public void onClickSocket(View view){
        Intent intent = new Intent(SelectiveSensorActivity.this, SocketActivity.class);
        startActivity(intent);
    }
    public void onClickDoor(View view){
        Intent intent = new Intent(SelectiveSensorActivity.this, DoorActivity.class);
        startActivity(intent);
    }

    public void onClickInfrared(View view){
        Intent intent = new Intent(SelectiveSensorActivity.this, InfraredActivity.class);
        startActivity(intent);
    }

}
