package com.example.attendancemanager;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static java.lang.Math.ceil;
import static java.lang.Math.floor;

public class MainActivity extends AppCompatActivity implements Adapter.OncustClick {
   private RecyclerView mrecyclerview;
   private ImageButton addsubbutton;
   private Toolbar mtoolbar;
   private RelativeLayout relativeLayout;
   private Adapter adapt;
   private SQLiteDatabase mdata;
   private int deletebutton=0;
   Adapter.OncustClick mclick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

        sqlitehelper help = new sqlitehelper(this);
        mdata = help.getWritableDatabase();

        mclick=MainActivity.this;

        mrecyclerview = findViewById(R.id.recycleview);
        mrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapt=new Adapter(this,getallitems(),mclick);
        mrecyclerview.setAdapter(adapt);

        relativeLayout=findViewById(R.id.rellayout);
        checkback();
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                     if(deletebutton==1)
                       removeitem((long) viewHolder.itemView.getTag());
                     else
                     {
                         Toast.makeText(MainActivity.this,"Enable Delete button first",Toast.LENGTH_SHORT).show();
                         adapt.swapcursor(getallitems());
                     }
            }
        }).attachToRecyclerView(mrecyclerview);

        addsubbutton = findViewById(R.id.addsubjectbutton);
        addsubbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addsub();
            }
        });

    }


    public void addsub()
    {
        LayoutInflater inflater=LayoutInflater.from(this);
        View v=inflater.inflate(R.layout.alertdialog,null);
        final AlertDialog.Builder alertdialog=new AlertDialog.Builder(this);
        final EditText alertsub=v.findViewById(R.id.alertsub);
        final EditText alertpresent=v.findViewById(R.id.alertpresent);
        final EditText alerttotal=v.findViewById(R.id.alerttotal);
        final EditText alertperc=v.findViewById(R.id.reqdper);
        alertdialog.setView(v);
        alertdialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).
                setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       String subjectname=alertsub.getText().toString();
                       String total=alerttotal.getText().toString();
                       String present=alertpresent.getText().toString();
                       String perc=alertperc.getText().toString();
                        if(subjectname.isEmpty())
                        {
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Error!!")
                                    .setMessage("Subject name can't be empty")
                                    .show();
                            return;
                        }
                        if(total.isEmpty())
                        {
                            total="0";
                        }
                        if(present.isEmpty())
                        {
                            present="0";
                        }
                        if(perc.isEmpty())
                        {
                            perc="75";
                        }
                       if(Integer.parseInt(total)<Integer.parseInt(present)) {
                           new AlertDialog.Builder(MainActivity.this)
                                   .setTitle("Error!!")
                                   .setMessage("Total lectures can't be Less then present lectures")
                                   .show();
                           return;
                       }
                        double de= Double.parseDouble(present)/Double.parseDouble(total)*100;
                        String percentile=String.format("%.1f",de);
                        String absent=Integer.toString(Integer.parseInt(total)- Integer.parseInt(present));
                        String col;
                        if(Double.parseDouble(percentile)>=Double.parseDouble(perc))
                            col="#00cc00";
                        else
                            col="#ff0000";
                        String bnk=canbunk(Double.parseDouble(perc),Double.parseDouble(percentile),Double.parseDouble(present),Double.parseDouble(absent));
                        if(percentile.equals("NaN"))
                        {
                            percentile="0.0";
                            bnk="No suggestions";
                        }
                        Calendar cale=Calendar.getInstance();
                        SimpleDateFormat sdf=new SimpleDateFormat(" HH:mm:ss dd MMMM yyyy");
                        String Curdate=sdf.format(cale.getTime());
                        ContentValues cv=new ContentValues();
                        cv.put(Tablevalues.Coloums.COLOUMN_SUBJECT, subjectname);
                        cv.put(Tablevalues.Coloums.COLOUMN_PERCENTAGE, percentile);
                        cv.put(Tablevalues.Coloums.COLOUMN_NOPRESENT,present);
                        cv.put(Tablevalues.Coloums.COLOUMN_NOABSENT, absent);
                        cv.put(Tablevalues.Coloums.COLOUMN_AIM, perc);
                        cv.put(Tablevalues.Coloums.COLOUMN_BUNK, bnk);
                        cv.put(Tablevalues.Coloums.COLOUMN_COLOR,col);
                        cv.put(Tablevalues.Coloums.COLOUMN_TIME,"Last updated:"+Curdate);
                        mdata.insert(Tablevalues.Coloums.TABLE_NAME, null, cv);
                        adapt.swapcursor(getallitems());
                        checkback();
                    }
                });
        AlertDialog a=alertdialog.create();
        a.show();

    }
    private void removeitem(long id)
    {
           mdata.delete(Tablevalues.Coloums.TABLE_NAME,Tablevalues.Coloums._ID+"="+id,null);
           adapt.swapcursor(getallitems());
            checkback();
    }
    private Cursor getallitems()
    {
        return mdata.query(
                Tablevalues.Coloums.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                Tablevalues.Coloums.COLOUMN_TIMESTAMP
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.item_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.reqdper:
                if(item.isChecked())
                {
                    item.setChecked(false);
                    deletebutton=0;
                }
                else
                {
                    item.setChecked(true);
                    deletebutton=1;
                    Toast.makeText(MainActivity.this,"Delete Enabled swipe left or right to delete",Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.help:
                Toast.makeText(MainActivity.this,"Implemented soon",Toast.LENGTH_SHORT).show();
                break;
            case R.id.updatehere:
                Intent mintent=new Intent(MainActivity.this,updatepasssword.class);
                startActivity(mintent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void OnitemClick(View view, int position,long id) {

        Cursor cursorc=getallitems();
        cursorc.moveToPosition(position);
        long pr=cursorc.getLong(cursorc.getColumnIndex(Tablevalues.Coloums._ID));
        double npresent=Double.parseDouble(cursorc.getString(cursorc.getColumnIndex(Tablevalues.Coloums.COLOUMN_NOPRESENT)))+1;
        double nabsent=Double.parseDouble(cursorc.getString(cursorc.getColumnIndex(Tablevalues.Coloums.COLOUMN_NOABSENT)));
        double npercent=npresent/(nabsent+npresent)*100;
        double naim=Double.parseDouble(cursorc.getString(cursorc.getColumnIndex(Tablevalues.Coloums.COLOUMN_AIM)));
        String scolor;
        if(npercent>=naim)
            scolor="#00cc00";
        else
            scolor="#ff0000";
        String spercent=String.format("%.1f",npercent);
        String spresent=String.format("%.0f",npresent);
        String saim=canbunk(naim,npercent,npresent,nabsent);
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat(" HH:mm:ss dd MMMM yyyy");
        String currentdate=sdf.format(calendar.getTime());
        ContentValues co=new ContentValues();
        co.put(Tablevalues.Coloums.COLOUMN_NOPRESENT,spresent);
        co.put(Tablevalues.Coloums.COLOUMN_PERCENTAGE,spercent);
        co.put(Tablevalues.Coloums.COLOUMN_TIME,"Last Updated:"+currentdate);
        co.put(Tablevalues.Coloums.COLOUMN_BUNK,saim);
        co.put(Tablevalues.Coloums.COLOUMN_COLOR,scolor);
        mdata.update(Tablevalues.Coloums.TABLE_NAME,co,"_ID="+pr,null);
        adapt.swapcursor(getallitems());
    }

    @Override
    public void OnAbsentClick(View view, int position) {
        Cursor cursorc=getallitems();
        cursorc.moveToPosition(position);
        long pr=cursorc.getLong(cursorc.getColumnIndex(Tablevalues.Coloums._ID));
        double npresent=Double.parseDouble(cursorc.getString(cursorc.getColumnIndex(Tablevalues.Coloums.COLOUMN_NOPRESENT)));
        double nabsent=Double.parseDouble(cursorc.getString(cursorc.getColumnIndex(Tablevalues.Coloums.COLOUMN_NOABSENT)))+1;
        double npercent=npresent/(nabsent+npresent)*100;
        double naim=Double.parseDouble(cursorc.getString(cursorc.getColumnIndex(Tablevalues.Coloums.COLOUMN_AIM)));
        String scolor;
        if(npercent>=naim)
            scolor="#00cc00";
        else
            scolor="#ff0000";
        String spercent=String.format("%.1f",npercent);
        String sabsent=String.format("%.0f",nabsent);
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat(" HH:mm:ss dd MMMM yyyy");
        String currentdate=sdf.format(calendar.getTime());
        String saim=canbunk(naim,npercent,npresent,nabsent);

        ContentValues co=new ContentValues();
        co.put(Tablevalues.Coloums.COLOUMN_NOABSENT,sabsent);
        co.put(Tablevalues.Coloums.COLOUMN_PERCENTAGE,spercent);
        co.put(Tablevalues.Coloums.COLOUMN_BUNK,saim);
        co.put(Tablevalues.Coloums.COLOUMN_TIME,"Last Updated:"+currentdate);
        co.put(Tablevalues.Coloums.COLOUMN_COLOR,scolor);
        mdata.update(Tablevalues.Coloums.TABLE_NAME,co,"_ID="+pr,null);
        adapt.swapcursor(getallitems());
    }
    public String canbunk(Double caim,Double cpercent,Double cpresent,Double cabsent)
    {
        String cbunk;
        if(cpercent>caim)
        {
            double value;
            value=floor((100*(cabsent+cpresent)-caim*(cabsent+cpresent)-100*cabsent)/caim);
            if(value==0.0)
                cbunk="Dont't miss next lecture";
            else
              cbunk="You can Bunk next "+String.format("%.0f",value)+" lectures";
        }
        else
        if(cpercent<caim)
        {
            double value;
            value=ceil(((cpresent+cabsent)*caim-100*cpresent)/(100-caim));
            cbunk="You must attend next "+String.format("%.0f",value)+" lectures";
        }
        else
        {
            cbunk="Don't miss next lecture";
        }
        return cbunk;
    }
    public void checkback()
    {
        Cursor crsr=mdata.rawQuery("SELECT COUNT(*) FROM "+Tablevalues.Coloums.TABLE_NAME,null);
        crsr.moveToFirst();
        if(crsr.getInt(0)==0)
            relativeLayout.setBackgroundResource(R.drawable.addimg);
        else
            relativeLayout.setBackgroundColor(Color.parseColor("#ffffff"));
    }

}
