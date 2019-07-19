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

public class updatepasssword extends AppCompatActivity {

     private EditText current,newp,confirm;
     private Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatepasssword);
        final Toolbar updatetool=findViewById(R.id.updatetoolbar);
        setSupportActionBar(updatetool);
        getSupportActionBar().setTitle("Update Password");
        current=findViewById(R.id.curpass);
        newp=findViewById(R.id.newpass);
        confirm=findViewById(R.id.confirmnewpass);
        update=findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old=current.getText().toString();
                SharedPreferences sharedPreferences=getSharedPreferences("PREFS",0);
                String curold=sharedPreferences.getString("KEY","");
                if(old.equals(curold))
                {
                    String mnew=newp.getText().toString();
                    String mcon=confirm.getText().toString();
                    if(mnew.length()<5||mnew.trim().isEmpty())
                    {
                        Toast.makeText(updatepasssword.this,"Password too short",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(mnew.equals(mcon))
                    {
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("KEY",mnew);
                        editor.apply();
                        Toast.makeText(updatepasssword.this,"Password updated successfully",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(updatepasssword.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(updatepasssword.this,"New Password does't matches",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(updatepasssword.this,"Current password incorrect",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
