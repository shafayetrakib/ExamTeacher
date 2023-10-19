package com.itbd.examnierteacher.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itbd.examnierteacher.Changepassword;
import com.itbd.examnierteacher.R;
import com.itbd.examnierteacher.SignIn;
import com.itbd.examnierteacher.personalinfo;

import java.time.Instant;
import java.time.temporal.TemporalAdjuster;


public class ProfileFragment extends Fragment {
    Activity context;
    private Uri imageUri;
    private static final int IMAGE_REQUEST=1;

    DatabaseReference databaseReference2;
    private Instant Picasso;
    private Object imageView;


    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context=getActivity();
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        ImageView imageView=view.findViewById(R.id.profile_pic);



        return view;
    }



    public void onStart(){



        //set profile name

        TextView teacherNmae=getView().findViewById(R.id.teacher_name);

        databaseReference2= FirebaseDatabase.getInstance().getReference("Teacher");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String FullName= String.valueOf(snapshot.child("Teacher").getValue());
                teacherNmae.setText(FullName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        super.onStart();
        ImageButton logout= (ImageButton) context.findViewById(R.id.btn_logout);
        ImageButton personalinfo= context.findViewById(R.id.personalinfo);
        ImageButton changepassword= context.findViewById(R.id.changepassword);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(context, SignIn.class));
            }
        });
        //
        personalinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,personalinfo.class));
            }
        });
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, Changepassword.class));
            }
        });
    }
}