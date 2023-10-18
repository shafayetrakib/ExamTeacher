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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itbd.examnierteacher.Dashboard;
import com.itbd.examnierteacher.R;
import com.itbd.examnierteacher.customAdapter;
import com.itbd.examnierteacher.datamanage.examsetupinfo;
import com.itbd.examnierteacher.examset;

import java.util.ArrayList;
import java.util.List;


public class DashFragment extends Fragment {
   private Activity context;
    ListView showexam;
    DatabaseReference databaseReference,databaseReference1;
    private List<examsetupinfo> eaxmlist;
     private  customAdapter CustomAdapter;









    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context=getActivity();

        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_dash, container, false);



        databaseReference=FirebaseDatabase.getInstance().getReference("ExamsetupInfo");

        eaxmlist=new ArrayList<>();
        CustomAdapter =new customAdapter(DashFragment.super.getActivity(),eaxmlist);

        showexam=view.findViewById(R.id.showexam);

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
                    examsetupinfo examinfo=snapshot1.getValue(examsetupinfo.class);
                    eaxmlist.add(examinfo);
                }
                showexam.setAdapter(CustomAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        super.onStart();



        //
        TextView examcreat= (TextView) context.findViewById(R.id.exam_creatfirst);
        examcreat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, examset.class));
            }
        });
    }
}