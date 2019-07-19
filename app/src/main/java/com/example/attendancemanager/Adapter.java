package com.example.attendancemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.ContentValues.TAG;

public class Adapter extends RecyclerView.Adapter<Adapter.Exampleviewholder> {
    private Context mcontext;
    private Cursor mcursor;
    public OncustClick mclicklistener;
    public Adapter(Context context,Cursor cursor,OncustClick mclicklistener){
        mcontext=context;
        mcursor=cursor;
        this.mclicklistener=mclicklistener;
    }

    public  static class Exampleviewholder extends RecyclerView.ViewHolder{
        public ImageButton maddbutton,msubtractbutton;
        public TextView msubject,mpresent,mabsent,mbunk,mpercentage,maim,mtime;
        public Exampleviewholder(@NonNull View itemView) {
            super(itemView);
            maddbutton= itemView.findViewById(R.id.add);
            msubtractbutton=itemView.findViewById(R.id.subtract);
            msubject=itemView.findViewById(R.id.subject);
           // msubject.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES |InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            mpresent=itemView.findViewById(R.id.noofpresent);
            mabsent=itemView.findViewById(R.id.noofabsent);
            mbunk=itemView.findViewById(R.id.canbunk);
            mpercentage=itemView.findViewById(R.id.percentage);
            maim=itemView.findViewById(R.id.aim);
            mtime=itemView.findViewById(R.id.date);
        }
    }

    @NonNull
    @Override
    public Exampleviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.singlecard,viewGroup,false);
        Exampleviewholder evh=new Exampleviewholder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final Exampleviewholder exampleviewholder, int i) {
          if(!mcursor.moveToPosition(i))
          {
              return;
          }
          String sub_name=mcursor.getString(mcursor.getColumnIndex(Tablevalues.Coloums.COLOUMN_SUBJECT));
          String sub_perc=mcursor.getString(mcursor.getColumnIndex(Tablevalues.Coloums.COLOUMN_PERCENTAGE));
          String sub_present=mcursor.getString(mcursor.getColumnIndex(Tablevalues.Coloums.COLOUMN_NOPRESENT));
          String sub_absent=mcursor.getString(mcursor.getColumnIndex(Tablevalues.Coloums.COLOUMN_NOABSENT));
          String sub_aim=mcursor.getString(mcursor.getColumnIndex(Tablevalues.Coloums.COLOUMN_AIM));
          String sub_bunk=mcursor.getString(mcursor.getColumnIndex(Tablevalues.Coloums.COLOUMN_BUNK));
          String sub_color=mcursor.getString(mcursor.getColumnIndex(Tablevalues.Coloums.COLOUMN_COLOR));
          String sub_date=mcursor.getString(mcursor.getColumnIndex(Tablevalues.Coloums.COLOUMN_TIME));
          long id=mcursor.getLong(mcursor.getColumnIndex(Tablevalues.Coloums._ID));
          exampleviewholder.msubject.setText(sub_name);
          exampleviewholder.mpercentage.setText(sub_perc);
          exampleviewholder.mpresent.setText(sub_present);
          exampleviewholder.mabsent.setText(sub_absent);
          exampleviewholder.maim.setText(sub_aim);
          exampleviewholder.mbunk.setText(sub_bunk);
          exampleviewholder.mtime.setText(sub_date);
          exampleviewholder.mpercentage.setTextColor(Color.parseColor(sub_color));
          exampleviewholder.itemView.setTag(id);
          final int pos=i;
          exampleviewholder.maddbutton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                          final long id=mcursor.getLong(mcursor.getColumnIndex(Tablevalues.Coloums._ID));
                          mclicklistener.OnitemClick(v,exampleviewholder.getLayoutPosition(),id);
              }
          });
          exampleviewholder.msubtractbutton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  mclicklistener.OnAbsentClick(v,exampleviewholder.getLayoutPosition());
              }
          });
    }
    @Override
    public int getItemCount() {
        return mcursor.getCount();
    }
    public void swapcursor(Cursor newcursor)
    {
        if(mcursor!=null)
            mcursor.close();

        mcursor=newcursor;
        if(newcursor!=null)
            notifyDataSetChanged();
    }
    public interface OncustClick{
        void OnitemClick(View view,int position,long id);
        void OnAbsentClick(View view,int position);
    }
}
