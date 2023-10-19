package com.itbd.examnierteacher;

import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itbd.examnierteacher.datamanage.signupInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Signup extends AppCompatActivity {
   EditText fullName,email,phone,autoPassword;
    TextView backTwo,signintext;
    Spinner courseName;
    Button signUp;
    ImageView visiablitySignup;

    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    // Variable for Course List
    private DatabaseReference mReference;
    List<String> courseListData = new ArrayList<>();
    TextView txtSelectCourse;

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
        visiablitySignup=findViewById(R.id.pass_invisi);

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
//        courseName = findViewById(R.id.select);

        mReference = FirebaseDatabase.getInstance().getReference();
        loadCourseList();

        // Making The Dialog
        BottomSheetDialog personalInfoDialog = new BottomSheetDialog(this, R.style.bottom_sheet_dialog);
        Objects.requireNonNull(personalInfoDialog.getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        personalInfoDialog.getBehavior().setSkipCollapsed(true);
        personalInfoDialog.getBehavior().setState(STATE_EXPANDED);
        personalInfoDialog.setContentView(R.layout.course_select_dialog);

        RelativeLayout courseSelectorLayout = findViewById(R.id.course_selector_layout);
        txtSelectCourse = findViewById(R.id.txt_select_course);
        courseSelectorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personalInfoDialog.show();
            }
        });

        // Making The List of Course
        ListView courseList = personalInfoDialog.findViewById(R.id.course_list);
        assert courseList != null;
        courseList.setAdapter(new ArrayAdapter<>(Signup.this,
                R.layout.course_list_item,
                R.id.txt_list_item, courseListData));

        courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                txtSelectCourse.setText(courseListData.get(i));
                personalInfoDialog.dismiss();
            }
        });

//        courseSelect =getResources().getStringArray(R.array.course);
//        ArrayAdapter coursename= new ArrayAdapter<String>(Signup.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,courseSelect);
//        courseName.setAdapter(coursename);


        //Hide or show password
        visiablitySignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(autoPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    autoPassword.setTransformationMethod(new PasswordTransformationMethod());
                    visiablitySignup.setImageResource(R.drawable.invisi_eye);
                }else {
                    autoPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    visiablitySignup.setImageResource(R.drawable.visi_eye);
                }
            }
        });


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
        String Course = txtSelectCourse.getText().toString().trim();

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
    private void loadCourseList(){
        mReference.child("courseList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courseListData.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    courseListData.add(dataSnapshot.getValue(String.class));
                }
                courseListData.remove("All");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Signup.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}