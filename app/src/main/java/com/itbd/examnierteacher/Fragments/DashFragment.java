package com.itbd.examnierteacher.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itbd.examnierteacher.R;
import com.itbd.examnierteacher.CustomAdapter;
import com.itbd.examnierteacher.DataMoldes.ExamDataModel;
import com.itbd.examnierteacher.DataMoldes.SignUpInfoModel;
import com.itbd.examnierteacher.ExamSetActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DashFragment extends Fragment {
    private static final String U_DATA = "arg1";
    ListView showExam;
    TextView txtUserName;
    DatabaseReference databaseReference;
    private List<ExamDataModel> eaxmlist;
    private com.itbd.examnierteacher.CustomAdapter CustomAdapter;

    SignUpInfoModel signUpInfoModelData;

    public DashFragment() {

    }

    public static DashFragment getInstance(SignUpInfoModel userData){
        DashFragment dashFragment = new DashFragment();
        Bundle bundle = new Bundle();

        bundle.putSerializable(U_DATA, userData);

        dashFragment.setArguments(bundle);
        return dashFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_dash, container, false);

        if (getArguments() != null){
            signUpInfoModelData = (SignUpInfoModel) getArguments().getSerializable(U_DATA);
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();
        loadExamSet();

        txtUserName = view.findViewById(R.id.txt_user_name);
        txtUserName.setText(signUpInfoModelData.getFullName());

        eaxmlist=new ArrayList<>();
        Collections.reverse(eaxmlist);
        CustomAdapter =new CustomAdapter(DashFragment.super.getActivity(),eaxmlist);

        showExam = view.findViewById(R.id.showexam);

        TextView btnExamCreate= (TextView) view.findViewById(R.id.exam_creatfirst);
        btnExamCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireActivity(), ExamSetActivity.class).putExtra("userCourse", signUpInfoModelData.getCourse()));
            }
        });

        return view;
    }

    private void loadExamSet(){
        databaseReference.child("examSet")
                .orderByChild("course")
                .equalTo(signUpInfoModelData.getCourse()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eaxmlist.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ExamDataModel examDataModel = dataSnapshot.getValue(ExamDataModel.class);
                    eaxmlist.add(examDataModel);
                }
                showExam.setAdapter(CustomAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireActivity(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}