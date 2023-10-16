package com.itbd.examnierteacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class startpage extends AppCompatActivity {
    Button login,getStarted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startpage);
        login = findViewById(R.id.have_account);
        getStarted = findViewById(R.id.getstart);

        getWindow().setStatusBarColor(ContextCompat.getColor(startpage.this,R.color.blue_pr));

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(startpage.this, SignIn.class);
                startActivity(intent);
            }
        });

        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(startpage.this, Signup.class);
                startActivity(intent);
            }
        });
    }
}