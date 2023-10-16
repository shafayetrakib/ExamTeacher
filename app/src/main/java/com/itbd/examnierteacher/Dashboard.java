package com.itbd.examnierteacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.itbd.examnierteacher.fragment.DashFragment;
import com.itbd.examnierteacher.fragment.ProfileFragment;
import com.itbd.examnierteacher.fragment.ResourceFragment;
import com.itbd.examnierteacher.fragment.ResultFragment;

public class Dashboard extends AppCompatActivity {

    BottomNavigationView bottonavbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        bottonavbar= findViewById(R.id.botton_navigationbar);

        getWindow().setStatusBarColor(ContextCompat.getColor(Dashboard.this,R.color.blue_pr));


        bottonavbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            int id = item.getItemId();
            
            if(id==R.id.profile){
                FragmentManager fm= getSupportFragmentManager();
                FragmentTransaction ft= fm.beginTransaction();
                ft.add(R.id.framelayout,new ProfileFragment());
                ft.commit();

            }  else if(id==R.id.dashboard){
                FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction ft= fm.beginTransaction();
                ft.add(R.id.framelayout,new DashFragment());
                ft.commit();

            }

            else if (id==R.id.result) {
                FragmentManager fm= getSupportFragmentManager();
                FragmentTransaction ft= fm.beginTransaction();
                ft.add(R.id.framelayout,new ResultFragment());
                ft.commit();
                
            } else if (id==R.id.resource) {
                FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.add(R.id.framelayout,new ResourceFragment());
                ft.commit();

            }

                return true;
            }
        });

        bottonavbar.setSelectedItemId(R.id.dashboard);

    }

}