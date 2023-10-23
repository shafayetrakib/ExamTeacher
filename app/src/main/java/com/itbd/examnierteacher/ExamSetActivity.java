package com.itbd.examnierteacher;

import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itbd.examnierteacher.DataMoldes.ExamDataModel;
import com.itbd.examnierteacher.DataMoldes.QuestionModel;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExamSetActivity extends AppCompatActivity {
    Button btnAddQuestion, btnExamSave;
    TextView examSetUpDate, examSetUpTime;
    EditText examSetUpName, examSetUpSyllabus, examSetUpMark, examSetUpDuration;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    DatabaseReference mReference;
    String userCourse, examName, examSyllabus, examTime, totalMarks, examDate, duration;
    boolean isQuestionAdded = false, isDataValidate = false;

    List<QuestionModel> questionModelList = new ArrayList<>();
    ExamDataModel examDataModel;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examset);
        getWindow().setStatusBarColor(ContextCompat.getColor(ExamSetActivity.this, R.color.blue_pr));

        mReference = FirebaseDatabase.getInstance().getReference();

        int identifyIntent = getIntent().getIntExtra("identifyIntent", -1);

        btnExamSave = findViewById(R.id.btn_save_exam);
        btnAddQuestion = findViewById(R.id.btn_addquestion);

        examSetUpName = findViewById(R.id.edt_exam_name);
        examSetUpSyllabus = findViewById(R.id.edt_exam_syllabus);
        examSetUpDate = findViewById(R.id.edt_exam_date);
        examSetUpTime = findViewById(R.id.edt_exam_time);
        examSetUpMark = findViewById(R.id.edt_exam_mark);
        examSetUpDuration = findViewById(R.id.edt_exam_duration);

        if (identifyIntent == 1) {
            // identifyIntent = 1, ExamSetActivity Open from create exam button
            userCourse = getIntent().getStringExtra("userCourse");
        } else if (identifyIntent == 2) {
            // identifyIntent = 2, ExamSetActivity Open from edit exam button
            examDataModel = (ExamDataModel) getIntent().getSerializableExtra("examData");

            assert examDataModel != null;
            userCourse = examDataModel.getCourse();

            examSetUpName.setText(examDataModel.getExamName());
            examSetUpSyllabus.setText(examDataModel.getExamSyllabus());
            examSetUpDate.setText(examDataModel.getExamDate());
            examSetUpTime.setText(examDataModel.getExamTime());
            examSetUpMark.setText(examDataModel.getTotalMarks());
            examSetUpDuration.setText(examDataModel.getDuration());

            questionModelList = examDataModel.getQuestionModelList();

            btnAddQuestion.setText("Show Question");
            btnExamSave.setText("Update");
        } else if (identifyIntent == 3) {
            // identifyIntent = 3, ExamSetActivity Open from save question button
            isQuestionAdded = getIntent().getBooleanExtra("isQuestionAdded", false);
            examDataModel = (ExamDataModel) getIntent().getSerializableExtra("examData");

            assert examDataModel != null;
            examSetUpName.setText(examDataModel.getExamName());
            examSetUpSyllabus.setText(examDataModel.getExamSyllabus());
            examSetUpDate.setText(examDataModel.getExamDate());
            examSetUpTime.setText(examDataModel.getExamTime());
            examSetUpMark.setText(examDataModel.getTotalMarks());
            examSetUpDuration.setText(examDataModel.getDuration());

            questionModelList = examDataModel.getQuestionModelList();
            userCourse = examDataModel.getCourse();

            btnAddQuestion.setText("Show Question");
        }

        //Date Picker for date select
        examSetUpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePicker datePicker = new DatePicker(ExamSetActivity.this);
                int currentDay = datePicker.getDayOfMonth();
                int currentMonth = (datePicker.getMonth());
                int currentYear = datePicker.getYear();

                datePickerDialog = new DatePickerDialog(ExamSetActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                examSetUpDate.setText(i2 + "/" + (i1 + 1) + "/" + i);

                            }
                        }, currentYear, currentMonth, currentDay);
                datePickerDialog.show();
            }
        });

        // timepicker for time select
        examSetUpTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePicker timepicker = new TimePicker(ExamSetActivity.this);
                int currentHour = timepicker.getCurrentHour();
                int currentMinute = timepicker.getCurrentMinute();

                timePickerDialog = new TimePickerDialog(ExamSetActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hours, int minute) {
                                Time time = new Time(hours, minute, 0);

                                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");

                                String s = simpleDateFormat.format(time);
                                examSetUpTime.setText(s);
                            }
                        }, currentHour, currentMinute, false);
                timePickerDialog.show();
            }
        });

        btnAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (identifyIntent == 1) {
                    examDataValidator();
                    if (isDataValidate) {
                        Intent goQuestionActivity = new Intent(ExamSetActivity.this, QuestionSetActivity.class);

                        ExamDataModel newEdm = new ExamDataModel();
                        newEdm.setCourse(userCourse);
                        newEdm.setExamName(examName);
                        newEdm.setExamSyllabus(examSyllabus);
                        newEdm.setExamDate(examDate);
                        newEdm.setExamTime(examTime);
                        newEdm.setTotalMarks(totalMarks);
                        newEdm.setDuration(totalMarks);

                        goQuestionActivity.putExtra("examData", newEdm);

                        startActivity(goQuestionActivity);
                        finish();
                    }
                } else if (identifyIntent == 2 || identifyIntent == 3) {
                    BottomSheetDialog showQuestionDialog = new BottomSheetDialog(ExamSetActivity.this, R.style.bottom_sheet_dialog);
                    Objects.requireNonNull(showQuestionDialog.getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    showQuestionDialog.getBehavior().setSkipCollapsed(true);
                    showQuestionDialog.getBehavior().setState(STATE_EXPANDED);

                    int screenHeight = getResources().getDisplayMetrics().heightPixels;

                    showQuestionDialog.setCancelable(false);
                    showQuestionDialog.setCanceledOnTouchOutside(true);
                    showQuestionDialog.setContentView(R.layout.bottom_dialog_show_question);

                    ListView listShowQuestion = showQuestionDialog.findViewById(R.id.list_dialog_show_question);
                    listShowQuestion.setAdapter(new BaseAdapter() {
                        @Override
                        public int getCount() {
                            return questionModelList.size();
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
                            if (view == null){
                                view = getLayoutInflater().inflate(R.layout.list_item_show_question, viewGroup, false);
                            }

                            TextView txtQuestionTitle = view.findViewById(R.id.txt_question_title);
                            TextView txtQuestionMark = view.findViewById(R.id.txt_question_mark);
                            TextView txtQuestionOptionOne = view.findViewById(R.id.txt_question_option_one);
                            TextView txtQuestionOptionTwo = view.findViewById(R.id.txt_question_option_two);
                            TextView txtQuestionOptionThree = view.findViewById(R.id.txt_question_option_three);
                            TextView txtQuestionOptionFour = view.findViewById(R.id.txt_question_option_four);
                            TextView txtQuestionOptionCorrect = view.findViewById(R.id.txt_question_option_correct);

                            QuestionModel questionModel = questionModelList.get(i);

                            txtQuestionTitle.setText(questionModel.getQuestion());
                            txtQuestionMark.setText(String.valueOf(questionModel.getQuestionMark()));
                            txtQuestionOptionOne.setText(questionModel.getOptionOne());
                            txtQuestionOptionTwo.setText(questionModel.getOptionTwo());
                            txtQuestionOptionThree.setText(questionModel.getOptionThree());
                            txtQuestionOptionFour.setText(questionModel.getOptionFour());
                            txtQuestionOptionCorrect.setText(questionModel.getCorrectOption());

                            return view;
                        }
                    });

                    showQuestionDialog.show();
                }
            }
        });

        // Button for saving and updating exam data
        btnExamSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                examDataValidator();
                if (identifyIntent == 1) {
                    if (isDataValidate) {
                        if (!isQuestionAdded) {
                            Toast.makeText(ExamSetActivity.this, "Please Add Question", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else if (identifyIntent == 2) {
                    DatabaseReference examUpdate = mReference.child("examSet").child(examDataModel.getExamId());
                    if (isDataValidate) {
                        if (!examName.equals(examDataModel.getExamName())) {
                            examUpdate.child("examName").setValue(examName);
                        }
                        if (!examSyllabus.equals(examDataModel.getExamSyllabus())) {
                            examUpdate.child("examSyllabus").setValue(examSyllabus);
                        }
                        if (!examDate.equals(examDataModel.getExamDate())) {
                            examUpdate.child("examDate").setValue(examDate);
                        }
                        if (!examTime.equals(examDataModel.getExamTime())) {
                            examUpdate.child("examTime").setValue(examTime);
                        }
                        if (!totalMarks.equals(examDataModel.getTotalMarks())) {
                            examUpdate.child("totalMarks").setValue(totalMarks);
                        }
                        if (!duration.equals(examDataModel.getDuration())) {
                            examUpdate.child("duration").setValue(duration);
                        }
                        Toast.makeText(ExamSetActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                } else if (identifyIntent == 3) {
                    String key = mReference.push().getKey();

                    examDataModel.setCourse(userCourse);
                    examDataModel.setExamId(key);

                    assert key != null;
                    mReference.child("examSet").child(key).setValue(examDataModel)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(ExamSetActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                }
                            });
                }
            }
        });

    }
    // Validating exam data
    public void examDataValidator() {
        examName = examSetUpName.getText().toString().trim();
        examSyllabus = examSetUpSyllabus.getText().toString().trim();
        examDate = examSetUpDate.getText().toString().trim();
        examTime = examSetUpTime.getText().toString().trim();
        totalMarks = examSetUpMark.getText().toString().trim();
        duration = examSetUpDuration.getText().toString().trim();

        if (examName.isEmpty()) {
            edtValidator(examSetUpName, "Please, Enter Title");
            return;
        }
        if (examSyllabus.isEmpty()) {
            edtValidator(examSetUpSyllabus, "Please, Enter Syllabus");
            return;
        }
        if (examDate.isEmpty()) {
            examSetUpDate.setError("Please, Enter Date");
            examSetUpDate.requestFocus();
            return;
        }
        if (examTime.isEmpty()) {
            examSetUpTime.setError("Please, Enter Time");
            examSetUpTime.requestFocus();
            return;
        }
        if (totalMarks.isEmpty()) {
            edtValidator(examSetUpMark, "Please, Enter Marks");
            return;
        }
        if (duration.isEmpty()) {
            edtValidator(examSetUpDuration, "Please, Enter Duration");
            return;
        }
        isDataValidate = true;
    }
    // Error setter on EditText
    private void edtValidator(EditText editText, String message) {
        editText.setError(message);
        editText.requestFocus();
    }
}