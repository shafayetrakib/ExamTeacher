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
import com.itbd.examnierteacher.datamanage.personaldata;
import com.itbd.examnierteacher.datamanage.setQuestion;

public class personalinfo extends AppCompatActivity {
    EditText fullname,email,phone,permanentAdd,presentAdd;
    Button saveInfo;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalinfo);
        fullname=findViewById(R.id.edit_fullname);
        email=findViewById(R.id.edit_email);
        phone=findViewById(R.id.edit_phone);
        permanentAdd=findViewById(R.id.edit_per_addresss);
        presentAdd=findViewById(R.id.edit_pre_addresss);
        saveInfo=findViewById(R.id.btn_saveinfo);
        getWindow().setStatusBarColor(ContextCompat.getColor(personalinfo.this,R.color.blue_pr));

        databaseReference= FirebaseDatabase.getInstance().getReference("PersonalInformation");

        saveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personalData();

            }
        });

    }
    public void personalData(){

        String Fullname=fullname.getText().toString().trim();
        String Email=email.getText().toString().trim();
        String Phone=phone.getText().toString().trim();
        String PermanentAdd=permanentAdd.getText().toString().trim();
        String PresentAdd=presentAdd.getText().toString().trim();

        if(TextUtils.isEmpty(Fullname)){
            fullname.setError("set your fullName");
            fullname.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(Email)){
            email.setError("set your EmailAddress");
            email.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(Phone)){
            phone.setError("set your PhoneNumber");
            phone.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(PermanentAdd)){
            permanentAdd.setError("set your permanent");
            permanentAdd.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(PresentAdd)){
            presentAdd.setError(" set your PresentAdd");
            presentAdd.requestFocus();
            return;
        }

        personaldata personal=new personaldata(Fullname,Email,Phone,PermanentAdd,PresentAdd);
        String key=databaseReference.push().getKey();
        databaseReference.child(key).setValue(personal);
        Toast.makeText(this, "Your Infor Successfully save", Toast.LENGTH_SHORT).show();

    }

}