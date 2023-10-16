package com.itbd.examnierteacher;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.itbd.examnierteacher.datamanage.examsetupinfo;
import com.itbd.examnierteacher.fragment.DashFragment;


import java.util.List;

public class customAdapter extends ArrayAdapter<examsetupinfo> {
    private Activity janinah;
    private List<examsetupinfo>eaxmlist;

    public customAdapter(Activity janinah, List<examsetupinfo> eaxmlist) {
        super(janinah, R.layout.list_item, eaxmlist);
        this.janinah = janinah;
        this.eaxmlist = eaxmlist;
    }




    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        LayoutInflater layoutInflater=janinah.getLayoutInflater();
       View view= layoutInflater.inflate(R.layout.list_item,null,false);

        examsetupinfo examinfo=eaxmlist.get(position);

        TextView itemOne=view.findViewById(R.id.item_one);
        TextView itemTwo=view.findViewById(R.id.item_two);
        TextView itemThree=view.findViewById(R.id.item_three);
        TextView itemFour=view.findViewById(R.id.item_four);
        TextView itemFive=view.findViewById(R.id.item_five);
        TextView itemSix=view.findViewById(R.id.item_six);

        itemOne.setText("Exam Name: "+examinfo.getExamsetupName());
        itemTwo.setText("Date: "+examinfo.getExamsetupDate());
        itemThree.setText("Time: "+examinfo.getExamsetupTime());
        itemFour.setText("Mark: "+examinfo.getExamsetupMark());
        itemFive.setText("Duration:"+examinfo.getExamsetupDuration());


        return view;
    }
}
