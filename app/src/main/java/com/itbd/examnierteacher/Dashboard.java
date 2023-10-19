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
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itbd.examnierteacher.datamanage.signupInfo;
import com.itbd.examnierteacher.fragment.DashFragment;
import com.itbd.examnierteacher.fragment.ProfileFragment;
import com.itbd.examnierteacher.fragment.ResourceFragment;
import com.itbd.examnierteacher.fragment.ResultFragment;

public class Dashboard extends AppCompatActivity {

    BottomNavigationView bottomNav;

    DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        String uID = getIntent().getStringExtra("uID");

        bottomNav = findViewById(R.id.botton_navigationbar);
        getWindow().setStatusBarColor(ContextCompat.getColor(Dashboard.this,R.color.blue_pr));

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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
                ft.add(R.id.framelayout,new DashFragment(uID));
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

        bottomNav.setSelectedItemId(R.id.dashboard);

    }



}