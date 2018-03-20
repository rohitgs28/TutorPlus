package com.dal.group7.tutorplus.ui.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dal.group7.tutorplus.R;

import java.util.Timer;
import java.util.TimerTask;

public class Splash_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Intent home_page = new Intent(Splash_Activity.this,HomePage_Activity.class);
                startActivity(home_page);
                finish();
            }}, 3000);
    }
}