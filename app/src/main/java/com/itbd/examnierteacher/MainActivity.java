package com.itbd.examnierteacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    int progess;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.blue_pr));

        progressBar  = (ProgressBar) findViewById(R.id.progessbar);
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
                startWindow();
            }
        });
        thread.start();

    }
    public void doWork()  {
        for(progess=20;progess<=100;progess=progess+1){

            try {
                Thread.sleep(50);
                progressBar.setProgress(progess);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }

    public void startWindow(){
        Intent intent =new Intent(MainActivity.this, startpage.class);
        startActivity(intent);
        finish();
    }

}
