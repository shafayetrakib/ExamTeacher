package com.itbd.examnierteacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itbd.examnierteacher.DataMoldes.ExamDataModel;
import com.itbd.examnierteacher.DataMoldes.QuestionModel;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ExamSetActivity extends AppCompatActivity {
    Button addQuestion, ok;
    TextView examSetUpDate, examSetUpTime;
    EditText examSetUpName, examSetUpSyllabus, examSetUpMark, examSetUpDuration;
    DatePickerDialog datePickerDailog;
    TimePickerDialog timePickerDialog;
    DatabaseReference databaseReference;
    String userCourse;
    boolean isQuestionAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examset);
        getWindow().setStatusBarColor(ContextCompat.getColor(ExamSetActivity.this, R.color.blue_pr));

        userCourse = getIntent().getStringExtra("userCourse");
        Toast.makeText(ExamSetActivity.this, ""+userCourse, Toast.LENGTH_SHORT).show();
        ok = findViewById(R.id.btn_examsetupok);
        addQuestion = findViewById(R.id.btn_addquestion);
        examSetUpDate = findViewById(R.id.edt_examsetupdate);
        examSetUpTime = findViewById(R.id.edt_examsetuptime);
        examSetUpName = findViewById(R.id.edt_examsetupname);
        examSetUpSyllabus = findViewById(R.id.edt_examsetupsyllabus);
        examSetUpMark = findViewById(R.id.edt_examsetupmark);
        examSetUpDuration = findViewById(R.id.edt_examsetupduration);


        databaseReference = FirebaseDatabase.getInstance().getReference();

        //for adding questions
        addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isQuestionAdded = true;
                startActivity(new Intent(ExamSetActivity.this, QuestionSetActivity.class));
            }
        });


        //Date Picker for date select
        examSetUpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePicker datePicker = new DatePicker(ExamSetActivity.this);
                int currentDay = datePicker.getDayOfMonth();
                int currentMonth = (datePicker.getMonth());
                int currentYear = datePicker.getYear();

                datePickerDailog = new DatePickerDialog(ExamSetActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                examSetUpDate.setText(i2 + "/" + (i1 + 1) + "/" + i);

                            }
                        }, currentYear, currentMonth, currentDay);
                datePickerDailog.show();
            }
        });

        // timepicker for time select
        examSetUpTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePicker timepicker = new TimePicker(ExamSetActivity.this);
                int currentHour = timepicker.getCurrentHour();
                int currentMinite = timepicker.getCurrentMinute();


                timePickerDialog = new TimePickerDialog(ExamSetActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hours, int minute) {
                                Time time = new Time(hours, minute, 0);

                                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");

                                String s = simpleDateFormat.format(time);
                                examSetUpTime.setText(s);
                            }
                        }, currentHour, currentMinite, false);
                timePickerDialog.show();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                examSetUp();
            }
        });
    }

    public void examSetUp() {
        String examName = examSetUpName.getText().toString().trim();
        String examSyllabus = examSetUpSyllabus.getText().toString().trim();
        String examDate = examSetUpDate.getText().toString().trim();
        String examTime = examSetUpTime.getText().toString().trim();
        String totalMarks = examSetUpMark.getText().toString().trim();
        String duration = examSetUpDuration.getText().toString().trim();

        if (TextUtils.isEmpty(examName)) {
            examSetUpName.setError("please Enter the ExamName");
            examSetUpName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(examSyllabus)) {
            examSetUpSyllabus.setError("please Enter the ExamName");
            examSetUpSyllabus.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(examDate)) {
            examSetUpDate.setError("please Enter the ExamName");
            examSetUpDate.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(examTime)) {
            examSetUpTime.setError("please Enter the ExamName");
            examSetUpTime.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(totalMarks)) {
            examSetUpMark.setError("please Enter the ExamName");
            examSetUpMark.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(duration)) {
            examSetUpDuration.setError("please Enter the ExamName");
            examSetUpDuration.requestFocus();
            return;
        }
        if (!isQuestionAdded){
            Toast.makeText(ExamSetActivity.this, "Please add question", Toast.LENGTH_SHORT).show();
            return;
        }

        List<QuestionModel> questionModelList = new ArrayList<>();
        List<String> usersList = new ArrayList<>();

        String key = databaseReference.push().getKey();

        assert key != null;
        databaseReference.child("examSet").child(key).setValue(new ExamDataModel(examName, examSyllabus,
                examTime, examDate, totalMarks, duration, userCourse, key, usersList, questionModelList));

        Toast.makeText(this, "exam setup successfully done", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(ExamSetActivity.this, DashboardActivity.class));
        finish();
    }
}