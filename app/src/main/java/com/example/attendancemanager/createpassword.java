package com.example.attendancemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class createpassword extends AppCompatActivity {
   private EditText password;
   private EditText confirmpassword;
   private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createpassword);
        Toolbar toolbar=findViewById(R.id.createtool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create Password");
        password=findViewById(R.id.password);
        confirmpassword=findViewById(R.id.confirmpassword);
        btn=findViewById(R.id.confirm);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pd1=password.getText().toString();
                String pd2=confirmpassword.getText().toString();
                if(pd1.length()<5)
                {
                    new AlertDialog.Builder(createpassword.this)
                            .setTitle("Error!")
                            .setMessage("Password is too weak")
                            .show();
                    return;
                }
                if(!pd1.equals(pd2))
                {
                    new AlertDialog.Builder(createpassword.this)
                            .setTitle("Error!")
                            .setMessage("PAsswor deoesn't Matches")
                            .show();
                    return;
                }
                SharedPreferences preferences=getSharedPreferences("PREFS",0);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("KEY",pd1);
                editor.apply();
                Toast.makeText(createpassword.this,"Password Created Successfully",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(createpassword.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
