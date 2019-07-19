package com.example.attendancemanager;

import static java.lang.Math.ceil;
import static java.lang.Math.floor;

public class getterandsetter {
    private int madd,msub;
    private String msubject,mpresent,mabsent,mbunk,mtotal,mpercentage,mper;

    public getterandsetter(int madd, int msub, String msubject, String mpresent, String mabsent, String mbunk,String mper) {
        this.madd = madd;
        this.msub = msub;
        this.msubject = msubject;
        this.mpresent = mpresent;
        this.mabsent = mabsent;
        this.mbunk = mbunk;
        this.mper=mper;
        this.mtotal=Integer.toString(Integer.parseInt(mpresent)+Integer.parseInt(mabsent));
    }

    public int getMadd() {
        return madd;
    }

    public int getMsub() {
        return msub;
    }

    public String getMsubject() {
        return msubject;
    }

    public String getMpresent() {
        return mpresent;
    }

    public String getMabsent() {
        return mabsent;
    }
    public String getMper()
    {
        return mper;
    }

    public String getMbunk() {
        if(Double.parseDouble(getMpercentage())>Double.parseDouble(mper))
        {
            Double value;
            value=floor((Double.parseDouble(mtotal)*100-Double.parseDouble(mper)*Double.parseDouble(mtotal)-100*Double.parseDouble(mabsent))/(Double.parseDouble(mper)));
            if(value==0.0)
                mbunk="Don't Bunk next lecture";
            else
                mbunk="You can Bunk next "+String.format("%.0f",value)+" lectures";
        }
        else
        if(Double.parseDouble(getMpercentage())<Double.parseDouble(mper))
        {
            Double value;
            value=ceil((Double.parseDouble(mtotal)*Double.parseDouble(mper)-100*Double.parseDouble(mpresent))/(100-Double.parseDouble(mper)));
            mbunk="Attend next "+String.format("%.0f",value)+" lectures";
        }
        else
        {
            mbunk="Don't miss next lecture";
        }
        return mbunk;
    }
    public String addone()
    {
        int a=Integer.parseInt(mpresent)+1;
        mpresent=Integer.toString(a);
        total();
        return mpresent;
    }
    public String addoneabsent()
    {
        int ab=Integer.parseInt(mabsent)+1;
        total();
        mabsent=Integer.toString(ab);
        return mabsent;
    }
    public String total()
    {
        int t=Integer.parseInt(mtotal)+1;
        mtotal=Integer.toString(t);
        return mtotal;
    }
    public String getMpercentage()
    {
        double d;
        d=(Double.parseDouble(mpresent)/Double.parseDouble(mtotal))*100;
        mpercentage=String.format("%.1f", d);
        if(mpercentage.equals("NaN"))
          mpercentage="0";
        return mpercentage;
    }

}
