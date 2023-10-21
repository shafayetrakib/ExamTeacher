package com.itbd.examnierteacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itbd.examnierteacher.DataMoldes.SignUpInfoModel;

public class PersonalInfoActivity extends AppCompatActivity {
    EditText fullName, email, phone, course;
    Button saveInfo;
    SignUpInfoModel signUpInfoModelData;
    DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalinfo);

        getWindow().setStatusBarColor(ContextCompat.getColor(PersonalInfoActivity.this,R.color.blue_pr));

        signUpInfoModelData = (SignUpInfoModel) getIntent().getSerializableExtra("uData");

        mRef = FirebaseDatabase.getInstance().getReference();

        fullName = findViewById(R.id.edit_fullname);
        email = findViewById(R.id.edit_email);
        phone = findViewById(R.id.edit_phone);
        course = findViewById(R.id.edit_course);
        saveInfo = findViewById(R.id.btn_saveinfo);

        email.setEnabled(false);
        course.setEnabled(false);

        fullName.setText(signUpInfoModelData.getFullName());
        email.setText(signUpInfoModelData.getEmail());
        phone.setText(signUpInfoModelData.getPhone());
        course.setText(signUpInfoModelData.getCourse());

        saveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personalData();
            }
        });

    }
    public void personalData(){

        String FullName = fullName.getText().toString().trim();
        String Phone = phone.getText().toString().trim();

        if(TextUtils.isEmpty(FullName)){
            fullName.setError("Please, Enter your name");
            fullName.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(Phone)){
            phone.setError("Please, Enter your phone number");
            phone.requestFocus();
            return;
        }

        mRef.child("Teacher").child(signUpInfoModelData.getuId()).setValue(new SignUpInfoModel(FullName,
                signUpInfoModelData.getEmail(), Phone, signUpInfoModelData.getCourse(), signUpInfoModelData.getuId()));

        Toast.makeText(PersonalInfoActivity.this, "Information Updated Successfully", Toast.LENGTH_SHORT).show();
    }

}