package com.itbd.examnierteacher;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.itbd.examnierteacher.DataMoldes.ExamDataModel;


import java.util.List;

public class CustomAdapter extends ArrayAdapter<ExamDataModel> {
    private Activity janinah;
    private List<ExamDataModel> eaxmlist;

    public CustomAdapter(Activity janinah, List<ExamDataModel> eaxmlist) {

        super(janinah, R.layout.exam_list_item, eaxmlist);
        this.janinah = janinah;
        this.eaxmlist = eaxmlist;
    }


    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = janinah.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.exam_list_item, parent, false);

        ExamDataModel examDataModel = eaxmlist.get(position);

        TextView itemOne = view.findViewById(R.id.item_one);
        TextView itemTwo = view.findViewById(R.id.item_two);
        TextView itemThree = view.findViewById(R.id.item_three);
        TextView itemFour = view.findViewById(R.id.item_four);
        TextView itemFive = view.findViewById(R.id.item_five);
        TextView itemSix = view.findViewById(R.id.item_six);

        ImageButton ExamDelete = view.findViewById(R.id.delete);
        ImageButton ExamEdit = view.findViewById(R.id.edit);

        itemOne.setText(examDataModel.getExamName());
        itemTwo.setText("Date : " + examDataModel.getExamDate());
        itemThree.setText("Time : " + examDataModel.getExamTime());
        itemFour.setText("Mark : " + examDataModel.getTotalMarks());
        itemFive.setText("Duration : " + examDataModel.getDuration() + " Minutes");


        ExamEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomAdapter.this.getContext(), ExamSetActivity.class);
                getContext().startActivity(intent);
            }
        });
        ExamDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = eaxmlist.get(position).getExamId();

                FirebaseDatabase.getInstance().getReference("examSet").child(key)
                        .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                notifyDataSetChanged();
                                Toast.makeText(getContext(), "Delete Successful", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        return view;
    }
}
