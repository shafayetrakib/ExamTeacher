package com.itbd.examnierteacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class SplashActivity extends AppCompatActivity {
    ProgressBar progressBar;
    int progess;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        getWindow().setStatusBarColor(ContextCompat.getColor(SplashActivity.this, R.color.blue_pr));

        progressBar = (ProgressBar) findViewById(R.id.progessbar);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
                startWindow();
            }
        });
        thread.start();

    }

    public void doWork() {
        for (progess = 20; progess <= 100; progess++) {

            try {
                Thread.sleep(20);
                progressBar.setProgress(progess);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void startWindow() {
        Intent intent = new Intent(SplashActivity.this, startpage.class);
        startActivity(intent);
        finish();
    }

}
