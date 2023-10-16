package com.itbd.examnierteacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itbd.examnierteacher.datamanage.signupInfo;

import java.util.Random;

public class Signup extends AppCompatActivity {
   EditText fullName,email,phone,autoPassword;
    TextView backTwo,signintext;
    Spinner courseName;
    Button signUp;

    String [] courseSelect;

    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getWindow().setStatusBarColor(ContextCompat.getColor(Signup.this,R.color.blue_pr));
        databaseReference= FirebaseDatabase.getInstance().getReference("Teacher");

        mAuth = FirebaseAuth.getInstance();

        fullName = findViewById(R.id.edt_fullname);
        email = findViewById(R.id.edt_email);
        phone = findViewById(R.id.edt_phone);
        signUp = findViewById(R.id.btn_Signup);
        backTwo = findViewById(R.id.backtwo);
        signintext=findViewById(R.id.signintext);
        autoPassword=findViewById(R.id.auto_password);


        //For auto Genarate Password
        autoPassword.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             autoPassword.setText(randompass(8));
         }
     });

        //go to signin activity
        signintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signup.this, SignIn.class));
            }
        });

        //back button previous activity
        backTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signup.this, startpage.class));
            }
        });

        //Spinner Course select
        courseName = findViewById(R.id.select);
        courseSelect =getResources().getStringArray(R.array.course);
        ArrayAdapter coursename= new ArrayAdapter<String>(Signup.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,courseSelect);
        courseName.setAdapter(coursename);
      //valid every Edit text field


        //working on Signup Button
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String FullName=fullName.getText().toString();
                String Phone=phone.getText().toString();
                String em=email.getText().toString().trim();
                String password=autoPassword.getText().toString().trim();

                if(em.isEmpty()){
                    email.setError("Enter a Email Address");
                    email.requestFocus();
                    return;
                }

                else if(!Patterns.EMAIL_ADDRESS.matcher(em).matches()){
                    email.setError("Enter a valid Email Address");
                    email.requestFocus();
                    return;
                }
                if(FullName.isEmpty()){
                    fullName.setError("Enter a FullName");
                    fullName.requestFocus();
                    return;
                }
                if(Phone.isEmpty()){
                    phone.setError("Enter a Phone Number");
                    phone.requestFocus();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(em,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                                 Toast.makeText(Signup.this, "Registion Succesfull", Toast.LENGTH_SHORT).show();
                                 
                        }else {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getApplicationContext(), "User already Registered", Toast.LENGTH_SHORT).show();
                              }else {
                                Toast.makeText(getApplicationContext(), "Error:"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                  saveData();
            }
        });

    }
    public void saveData(){
        String Fullname= fullName.getText().toString().trim();
        String Email= email.getText().toString().trim();
        String Phone= phone.getText().toString().trim();
        String Course= courseName.getSelectedItem().toString().trim();

        if(TextUtils.isEmpty(Fullname)){
            fullName.setError("Full name is required");
            fullName.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(Email) && !Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(Phone)){
            phone.setError("phone is required");
            phone.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(Course)){

            courseName.requestFocus();
            return;
        }

        signupInfo info= new signupInfo(Fullname,Email,Phone,Course);
        String key=databaseReference.push().getKey();
        databaseReference.child(key).setValue(info);
        Toast.makeText(this, "You are Successfully Registread", Toast.LENGTH_SHORT).show();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Signup.this);
        alertDialogBuilder.setTitle("Alert");
        alertDialogBuilder.setMessage("You have got a auto Genarated password");
        alertDialogBuilder.setIcon(R.drawable.warning);
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                startActivity(new Intent(Signup.this, SignIn.class));
            }
        });
        AlertDialog alertDialog= alertDialogBuilder.create();
        alertDialog.show();

    }

    public String randompass(int length){

    char[] chars="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
        Random r= new Random();
        StringBuilder sb= new StringBuilder();


        for(int i=0;i<length;i++){
            char c=chars[r.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }

}