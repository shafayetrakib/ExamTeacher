package com.itbd.examnierteacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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
import com.itbd.examnierteacher.datamanage.ExamDataModel;
import com.itbd.examnierteacher.datamanage.QuestionModel;

import java.util.ArrayList;
import java.util.List;

public class examset extends AppCompatActivity {
    Button addQuestion,ok;
    TextView examsetupDate,examsetupTime;
    EditText examsetupName,examsetupSyllabus,examsetupMark,examsetupDuration;
    DatePickerDialog datePickerDailog;
    TimePickerDialog timePickerDialog;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examset);

        ok=findViewById(R.id.btn_examsetupok);
        addQuestion = findViewById(R.id.btn_addquestion);
        examsetupDate = findViewById(R.id.edt_examsetupdate);
        examsetupTime = findViewById(R.id.edt_examsetuptime);
        examsetupName=findViewById(R.id.edt_examsetupname);
        examsetupSyllabus=findViewById(R.id.edt_examsetupsyllabus);
        examsetupMark=findViewById(R.id.edt_examsetupmark);
        examsetupDuration = findViewById(R.id.edt_examsetupduration);

        getWindow().setStatusBarColor(ContextCompat.getColor(examset.this,R.color.blue_pr));

      databaseReference = FirebaseDatabase.getInstance().getReference();

        //for adding questions
        addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(examset.this, question.class));
            }
        });


//Date Picker for date select
        examsetupDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePicker datePicker=new DatePicker(examset.this);
                int currentDay= datePicker.getDayOfMonth();
                int currentMonth= (datePicker.getMonth()+1);
                int currentYear= datePicker.getYear();

                datePickerDailog = new DatePickerDialog(examset.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            examsetupDate.setText(i2+"/"+(i1+1)+"/"+i);

                        }
                    },currentYear,currentMonth,currentDay );
                datePickerDailog.show();
            }
        });

    // timepicker for time select
        examsetupTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePicker timepicker = new TimePicker(examset.this);
                int currentHoure=timepicker.getCurrentHour();
                int currentMinite=timepicker.getCurrentMinute();


                timePickerDialog =new TimePickerDialog(examset.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hours, int minute) {
                                String ampm;
                                if(hours>= 12){
                                    ampm="PM";
                                }else {
                                    ampm="AM";
                                }
                                examsetupTime.setText(hours+":"+minute+" "+ampm);
                            }
                        },currentHoure,currentMinite,true);
                timePickerDialog.show();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                examsetUp();
                startActivity(new Intent(examset.this, Dashboard.class));
                finish();
            }
        });


    }
    public void examsetUp(){
        String examName = examsetupName.getText().toString().trim();
        String examSyllabus = examsetupSyllabus.getText().toString().trim();
        String examDate = examsetupDate.getText().toString().trim();
        String examTime = examsetupTime.getText().toString().trim();
        String totalMarks = examsetupMark.getText().toString().trim();
        String duration = examsetupDuration.getText().toString().trim();

        if(TextUtils.isEmpty(examName)){
            examsetupName.setError("please Enter the ExamName");
            examsetupName.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(examSyllabus)){
            examsetupSyllabus.setError("please Enter the ExamName");
            examsetupSyllabus.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(examDate)){
            examsetupDate.setError("please Enter the ExamName");
            examsetupDate.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(examTime)){
            examsetupTime.setError("please Enter the ExamName");
            examsetupTime.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(totalMarks)){
            examsetupMark.setError("please Enter the ExamName");
            examsetupMark.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(duration)){
            examsetupDuration.setError("please Enter the ExamName");
            examsetupDuration.requestFocus();
            return;
        }

        List<QuestionModel> questionModelList = new ArrayList<>();
        List<String> usersList = new ArrayList<>();

        String key=databaseReference.push().getKey();

        assert key != null;
        databaseReference.child("examSet").child(key).setValue(new ExamDataModel(examName, examSyllabus,
                examTime, examDate, totalMarks, duration,"Android App Development", key, usersList, questionModelList));

        Toast.makeText(this, "exam setup successfully done", Toast.LENGTH_SHORT).show();
    }
}