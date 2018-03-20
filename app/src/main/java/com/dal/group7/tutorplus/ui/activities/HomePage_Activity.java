package com.dal.group7.tutorplus.ui.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dal.group7.tutorplus.R;


public class HomePage_Activity extends AppCompatActivity {
    Intent goToLogin;
    static int flag=0;
    ConnectionDetect connect;
    public CharSequence con = "You're connected to the internet";
    public static boolean isStudentLogin = false;
    int duration = Toast.LENGTH_LONG;
    Button button;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_layout);

        Button tutorbtn = (Button) findViewById(R.id.tutorbtn);
        Button studentbtn = (Button) findViewById(R.id.studentbtn);

        goToLogin = new Intent(HomePage_Activity.this, GoogleLogin_Activity.class);


        tutorbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePage_Activity.this.startActivity(goToLogin);
                isStudentLogin = false;
                flag=1;
            }
        });
        studentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePage_Activity.this.startActivity(goToLogin);
                isStudentLogin = true;
                flag=2;
            }
        });

        connect = new ConnectionDetect(this);

        if (connect.isconnect()) {
        } else {
            showDialog();
        }
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomePage_Activity.this);
        builder.setMessage("Connect to wifi or quit")
                .setCancelable(false)
                .setPositiveButton("Connect to WIFI", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        HomePage_Activity.this.finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}