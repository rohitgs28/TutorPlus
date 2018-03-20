package com.dal.group7.tutorplus.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dal.group7.tutorplus.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button studentBtn;
    private Button tutorBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        studentBtn = (Button)findViewById(R.id.studentBtn);
        studentBtn.setOnClickListener(this);
        tutorBtn = (Button)findViewById(R.id.tutorBtn);
        tutorBtn.setOnClickListener(this);
    }

    /**
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, LoginByGoogleActivity.class);
        int id = view.getId();
        switch(id){
            case R.id.studentBtn:
                intent.putExtra("role","student");
                break;
            case R.id.tutorBtn:
                intent.putExtra("role","tutor");
                break;
        }
        startActivity(intent);
    }
}
