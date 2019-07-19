package com.example.attendancemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences preferences=getSharedPreferences("PREFS",0);
        final String text=preferences.getString("KEY","");
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
           if(text.isEmpty())
            {
                 Intent  intent=new Intent(splash.this,createpassword.class);
                 startActivity(intent);
                 finish();
           }
          else
            {
                    Intent intent=new Intent(splash.this,password.class);
                   startActivity(intent);
                finish();
                }
            }
        },2000);
    }
}
