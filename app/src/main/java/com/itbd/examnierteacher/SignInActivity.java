package com.itbd.examnierteacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {
    private static final String FILE_NAME = "MyFile";
    public static final String SHEARD_PREFS = "SheadrPrefs";
    EditText passwordSignin, emailSignin;
    TextView mainforgot, signuptext;
    ImageView visiablity;
    Button signin;
    TextView backOne;
    private FirebaseAuth mAuth;
    CheckBox rememberMe;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailSignin = findViewById(R.id.email_signin);
        passwordSignin = findViewById(R.id.passwor_signin);
        visiablity = findViewById(R.id.pass_invisiable);
        signin = findViewById(R.id.btn_Signin);
        mainforgot = findViewById(R.id.password_forgot);
        backOne = findViewById(R.id.backone);
        signuptext = findViewById(R.id.signuptext);
        rememberMe = findViewById(R.id.rememberme);
        progressBar = findViewById(R.id.progresss);

        getWindow().setStatusBarColor(ContextCompat.getColor(SignInActivity.this, R.color.blue_pr));

        SharedPreferences sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        String LoginEmail = sharedPreferences.getString("mail", "");
        String LoginPassword = sharedPreferences.getString("signinpassword", "");
        emailSignin.setText(LoginEmail);
        passwordSignin.setText(LoginPassword);

        mAuth = FirebaseAuth.getInstance();

        //go to sinup activity
        signuptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });

        // back button for back activity
        backOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        //Forgot password
        mainforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, ForgotPasswordActivity.class));
            }
        });
        //Hide or show password
        visiablity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passwordSignin.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    passwordSignin.setTransformationMethod(new PasswordTransformationMethod());
                    visiablity.setImageResource(R.drawable.invisi_eye);
                } else {
                    passwordSignin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    visiablity.setImageResource(R.drawable.visi_eye);
                }
            }
        });
        // Work signIn Activity with signup button
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start rememberMe
                String Mail = emailSignin.getText().toString().trim();
                String signinpassword = passwordSignin.getText().toString().trim();
                if (rememberMe.isChecked()) {
                    StoreDatausingShared(Mail, signinpassword);
                }

                //End of remember Me

                String email = emailSignin.getText().toString().trim();
                String password = passwordSignin.getText().toString().trim();

                if (email.isEmpty()) {
                    emailSignin.setError("Enter a Email Adress");
                    emailSignin.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailSignin.setError("Enter a valid Email Address");
                    emailSignin.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    passwordSignin.setError("Enter a password");
                    passwordSignin.requestFocus();
                    return;
                }
                if (password.length() < 8) {
                    passwordSignin.setError("Minimum eight digit password ");
                    passwordSignin.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);


                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            assert user != null;
                            String uID = user.getUid();

                            startActivity(new Intent(SignInActivity.this, DashboardActivity.class).putExtra("uID", uID));
                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(), "SignIn Unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


    }


    private void StoreDatausingShared(String mail, String signinpassword) {
        SharedPreferences.Editor editor = getSharedPreferences(FILE_NAME, MODE_PRIVATE).edit();
        editor.putString("mail", mail);
        editor.putString("signinpassword", signinpassword);
        editor.apply();
    }
}