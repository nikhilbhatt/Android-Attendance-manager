package com.example.attendancemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class password extends AppCompatActivity {
    private EditText textpass;
    private Button subbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        Toolbar tool=findViewById(R.id.passwordtoolbar);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("Password");
        textpass=findViewById(R.id.pass);


        subbtn=findViewById(R.id.passbutton);
        subbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tex=textpass.getText().toString();
                SharedPreferences prefs=getSharedPreferences("PREFS",0);
                String getpass;
                getpass=prefs.getString("KEY","");
                if(tex.equals(getpass))
                {
                    Intent intent=new Intent(password.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(password.this,"Password is Incorrect",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
