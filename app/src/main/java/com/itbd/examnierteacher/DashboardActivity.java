package com.itbd.examnierteacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itbd.examnierteacher.DataMoldes.SignUpInfoModel;
import com.itbd.examnierteacher.Fragments.DashFragment;
import com.itbd.examnierteacher.Fragments.ProfileFragment;
import com.itbd.examnierteacher.Fragments.ResourceFragment;
import com.itbd.examnierteacher.Fragments.ResultFragment;

import java.util.Objects;

public class DashboardActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    SignUpInfoModel signUpInfoModelData;
    String uID, uName, uCourse;
    DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();

    Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        loadingDialog = new Dialog(DashboardActivity.this);

        Objects.requireNonNull(loadingDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingDialog.setContentView(R.layout.progressbar_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.show();

        uID = getIntent().getStringExtra("uID");

        bottomNav = findViewById(R.id.botton_navigationbar);
        getWindow().setStatusBarColor(ContextCompat.getColor(DashboardActivity.this,R.color.blue_pr));

        loadUserData();

        bottomNav.getMenu().findItem(R.id.dashboard).setChecked(true);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId() == R.id.profile){
                    loadFrag(ProfileFragment.getInstance(signUpInfoModelData), 1);
                } else if (item.getItemId() == R.id.dashboard) {
                    loadFrag(DashFragment.getInstance(signUpInfoModelData), 1);
                } else if (item.getItemId() == R.id.result) {
                    loadFrag(new ResultFragment(), 1);
                } else if (item.getItemId() == R.id.resource) {
                    loadFrag(ResourceFragment.getInstance(uID, uName, uCourse), 1);
                }

                return true;
            }
        });
    }
    public void loadFrag(Fragment fragment, int flag){

        FragmentManager fragManager = getSupportFragmentManager();
        FragmentTransaction fragTrans = fragManager.beginTransaction();

        if (flag == 0){
            fragTrans.add(R.id.frame_Layout, fragment);
        }else{
            fragTrans.replace(R.id.frame_Layout, fragment);
        }

        fragTrans.commit();
    }

    public void loadUserData(){
        mReference.child("Teacher").child(uID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                signUpInfoModelData = snapshot.getValue(SignUpInfoModel.class);

                assert signUpInfoModelData != null;
                uName = signUpInfoModelData.getFullName();
                uCourse = signUpInfoModelData.getCourse();

                loadFrag(DashFragment.getInstance(signUpInfoModelData), 0);
                loadingDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DashboardActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}