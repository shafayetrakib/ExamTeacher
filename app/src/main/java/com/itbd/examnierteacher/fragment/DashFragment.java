package com.itbd.examnierteacher.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itbd.examnierteacher.R;
import com.itbd.examnierteacher.customAdapter;
import com.itbd.examnierteacher.datamanage.ExamDataModel;
import com.itbd.examnierteacher.examset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DashFragment extends Fragment {
   private Activity context;
    ListView showExam;
    DatabaseReference databaseReference;
    private List<ExamDataModel> eaxmlist;
     private  customAdapter CustomAdapter;









    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context=getActivity();

        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_dash, container, false);



        databaseReference=FirebaseDatabase.getInstance().getReference("examSet");

        eaxmlist=new ArrayList<>();
        Collections.reverse(eaxmlist);
        CustomAdapter =new customAdapter(DashFragment.super.getActivity(),eaxmlist);

        showExam = view.findViewById(R.id.showexam);

        //




        return view;
    }

    
    public void onStart(){



        //
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eaxmlist.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    ExamDataModel examinfo = snapshot1.getValue(ExamDataModel.class);
                    eaxmlist.add(examinfo);
                }
                showExam.setAdapter(CustomAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        super.onStart();



        //
        TextView btnExamCreate= (TextView) context.findViewById(R.id.exam_creatfirst);
        btnExamCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, examset.class));
            }
        });
    }
}