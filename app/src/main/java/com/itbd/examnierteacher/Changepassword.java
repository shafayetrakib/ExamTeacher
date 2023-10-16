package com.itbd.examnierteacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Changepassword extends AppCompatActivity {
    EditText changePassword;
    ImageView visiable;
    Button saveChange;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        changePassword= findViewById(R.id.changepassword);
        saveChange=findViewById(R.id.savechangetwo);
        visiable=findViewById(R.id.Newpass_invisiable);

        getWindow().setStatusBarColor(ContextCompat.getColor(Changepassword.this,R.color.blue_pr));

        mAuth = FirebaseAuth.getInstance();

        //visibilty button

        visiable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(changePassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    changePassword.setTransformationMethod(new PasswordTransformationMethod());
                    visiable.setImageResource(R.drawable.invisi_eye);
                }else {
                    changePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    visiable.setImageResource(R.drawable.visi_eye);
                }
            }
        });



        saveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Changepassword=changePassword.getText().toString().trim();
                if(Changepassword.isEmpty()){
                    changePassword.setError("Enter a Password");
                    changePassword.requestFocus();
                    return;
                }
                if (Changepassword.length()>8){
                    changePassword.setError("Password must be 8 characters");
                    changePassword.requestFocus();
                    return;
                }
                FirebaseUser user=mAuth.getCurrentUser();
                user.updatePassword(Changepassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Changepassword.this, "your password is changed successfully", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(Changepassword.this, SignIn.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Changepassword.this, "Password reset fuailure", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}