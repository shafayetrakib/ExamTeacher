package com.itbd.examnierteacher;

import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.itbd.examnierteacher.DataMoldes.CourseDataModel;
import com.itbd.examnierteacher.DataMoldes.TeacherDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class SignUpActivity extends AppCompatActivity {
    EditText fullName, email, phone, autoPassword;
    TextView backTwo, signInText;
    Button signUp;
    ImageView visibilitySignUp;
    ProgressBar progressBar, dialogProgress;
    ListView courseList;

    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    // Variable for Course List
    private DatabaseReference mReference;
    List<CourseDataModel> courseListData = new ArrayList<>();
    TextView txtSelectCourse, txtSelectPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getWindow().setStatusBarColor(ContextCompat.getColor(SignUpActivity.this, R.color.blue_pr));

        mReference = FirebaseDatabase.getInstance().getReference();

        databaseReference = FirebaseDatabase.getInstance().getReference("Teacher");

        mAuth = FirebaseAuth.getInstance();

        fullName = findViewById(R.id.edt_fullname);
        email = findViewById(R.id.edt_email);
        phone = findViewById(R.id.edt_phone);
        signUp = findViewById(R.id.btn_Signup);
        backTwo = findViewById(R.id.backtwo);
        signInText = findViewById(R.id.signintext);
        autoPassword = findViewById(R.id.auto_password);
        visibilitySignUp = findViewById(R.id.pass_invisi);

        //For auto Generated Password
        autoPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autoPassword.setText(randomPass(8));
            }
        });

        //go to Sign In activity
        signInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //back button previous activity
        backTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        RelativeLayout courseSelectorLayout = findViewById(R.id.course_selector_layout);
        txtSelectCourse = findViewById(R.id.txt_select_course);
        courseSelectorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCourseDialog();
            }
        });

        RelativeLayout positionSelectorLayout = findViewById(R.id.position_selector_layout);
        txtSelectPosition = findViewById(R.id.txt_select_position);
        positionSelectorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog positionSelectDialog = new Dialog(SignUpActivity.this);
                positionSelectDialog.setContentView(R.layout.bottom_dialog_course_select);
                Objects.requireNonNull(positionSelectDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                positionSelectDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                positionSelectDialog.getWindow().setGravity(Gravity.BOTTOM);

                String[] allPositions = {"Senior Teacher", "Assistant Teacher", "Junior Teacher"};

                ProgressBar positionDialogProgressBar = positionSelectDialog.findViewById(R.id.progress_bar);
                TextView positionDialogTitle = positionSelectDialog.findViewById(R.id.course_dialog_title);
                positionDialogTitle.setText("Select Your Position");

                ListView positionList = positionSelectDialog.findViewById(R.id.course_list);
                positionList.setAdapter(new ArrayAdapter<String>(SignUpActivity.this, R.layout.list_item_course, R.id.txt_list_item, allPositions));

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        positionDialogProgressBar.setVisibility(View.GONE);
                    }
                }, 100);

                positionSelectDialog.show();

                positionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        txtSelectPosition.setText(allPositions[i]);
                        positionSelectDialog.dismiss();
                    }
                });

            }
        });

        //Hide or show password
        visibilitySignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (autoPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    autoPassword.setTransformationMethod(new PasswordTransformationMethod());
                    visibilitySignUp.setImageResource(R.drawable.invisi_eye);
                } else {
                    autoPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    visibilitySignUp.setImageResource(R.drawable.visi_eye);
                }
            }
        });

        //working on Signup Button
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String FullName = fullName.getText().toString().trim();
                String Email = email.getText().toString().trim();
                String Phone = phone.getText().toString().trim();
                String Position = txtSelectPosition.getText().toString().trim();
                String Course = txtSelectCourse.getText().toString().trim();
                String password = autoPassword.getText().toString().trim();

                if (FullName.isEmpty()) {
                    fullName.setError("Please enter your name");
                    fullName.requestFocus();
                    return;
                }
                if (Email.isEmpty()) {
                    email.setError("Please enter your email address");
                    email.requestFocus();
                    return;
                }
                if (!Email.matches("^[a-zA-z0-9_\\-]*@gmail\\.com$")) {
                    email.setError("Please enter a valid email address");
                    email.requestFocus();
                    return;
                }
                if (Phone.isEmpty()) {
                    phone.setError("Please enter your phone number");
                    phone.requestFocus();
                    return;
                }
                if (Position.isEmpty()) {
                    txtSelectPosition.setError("Please select your position");
                    txtSelectPosition.requestFocus();
                    return;
                }
                if (Course.isEmpty()) {
                    txtSelectCourse.setError("Please select your course");
                    txtSelectCourse.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    autoPassword.setError("Please enter your password");
                    autoPassword.requestFocus();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(Email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();

                            assert user != null;
                            String uId = user.getUid();

                            TeacherDataModel teacherDataModel = new TeacherDataModel(FullName, Position, Email, Phone, Course, uId);
                            saveData(uId, teacherDataModel);

                        } else {
                            email.setError("Please enter a different email");
                            email.requestFocus();
                        }
                    }
                });
            }
        });

    }

    public void saveData(String uId, TeacherDataModel teacherDataModel) {
        databaseReference.child(uId).setValue(teacherDataModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(SignUpActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                finish();
            }
        });

    }

    public String randomPass(int length) {
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz!@#$%^&*".toCharArray();
        Random r = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            char c = chars[r.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }

    private void showCourseDialog() {
        Dialog courseSelectDialog = new Dialog(SignUpActivity.this);
        courseSelectDialog.setContentView(R.layout.bottom_dialog_course_select);
        Objects.requireNonNull(courseSelectDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        courseSelectDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        courseSelectDialog.getWindow().setGravity(Gravity.BOTTOM);

        dialogProgress = courseSelectDialog.findViewById(R.id.progress_bar);
        courseList = courseSelectDialog.findViewById(R.id.course_list);
        loadCourseList(courseList);

        courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                txtSelectCourse.setText(courseListData.get(i).getCourseName());

                courseSelectDialog.dismiss();
            }
        });
        courseSelectDialog.show();
    }

    private void loadCourseList(ListView listView) {
        mReference.child("courseList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courseListData.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    courseListData.add(dataSnapshot.getValue(CourseDataModel.class));
                }
                courseListData.removeIf(CourseDataModel -> CourseDataModel.getCourseName().equals("All"));
                dialogProgress.setVisibility(View.GONE);

                listView.setAdapter(new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return courseListData.size();
                    }

                    @Override
                    public Object getItem(int i) {
                        return null;
                    }

                    @Override
                    public long getItemId(int i) {
                        return 0;
                    }

                    @Override
                    public View getView(int i, View view, ViewGroup viewGroup) {
                        if (view == null) {
                            view = getLayoutInflater().inflate(R.layout.list_item_course, viewGroup, false);
                        }
                        CourseDataModel courseDataModel = courseListData.get(i);

                        TextView txtListItem = view.findViewById(R.id.txt_list_item);

                        txtListItem.setText(courseDataModel.getCourseName());

                        return view;
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SignUpActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}