package com.itbd.examnierteacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itbd.examnierteacher.DataMoldes.QuestionModel;

import java.util.ArrayList;
import java.util.List;

public class QuestionSetActivity extends AppCompatActivity {

    TextView txtQuestionNo, txtExamTotalMarks, txtQuestionTotalMarks;
    AppCompatButton btnAddQuestion, btnSaveQuestion;
    EditText edtQuestion,
            edtOptionOne, edtOptionTwo, edtOptionThree, edtOptionFour;
    RadioGroup radioAnsGrp, radioMarkGrp;

    RadioButton checkOne, checkTwo, checkThree, checkFour,
                markOne, markTwo, markThree;

    String question, optionOne, optionTwo, optionThree, optionFour, correctAns;
    int questionMark, totalQuestionMark, totalExamMark = 30, questionNo = 1;

    List<QuestionModel> questionModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        getWindow().setStatusBarColor(ContextCompat.getColor(QuestionSetActivity.this, R.color.blue_pr));

        txtQuestionNo = findViewById(R.id.txt_question_no);
        txtExamTotalMarks = findViewById(R.id.txt_exam_total_marks);
        txtQuestionTotalMarks = findViewById(R.id.txt_question_total_marks);

        edtQuestion = findViewById(R.id.edt_question);
        edtOptionOne = findViewById(R.id.edt_option_one);
        edtOptionTwo = findViewById(R.id.edt_option_two);
        edtOptionThree = findViewById(R.id.edt_option_three);
        edtOptionFour = findViewById(R.id.edt_option_four);

        radioAnsGrp = findViewById(R.id.radio_ans_grp);
        radioMarkGrp = findViewById(R.id.radio_mark_grp);

        checkOne = findViewById(R.id.checkbox_one);
        checkTwo = findViewById(R.id.checkbox_two);
        checkThree = findViewById(R.id.checkbox_three);
        checkFour = findViewById(R.id.checkbox_four);

        markOne = findViewById(R.id.q_mark_one);
        markTwo = findViewById(R.id.q_mark_two);
        markThree = findViewById(R.id.q_mark_three);

        btnAddQuestion = findViewById(R.id.btn_add_question);
        btnSaveQuestion = findViewById(R.id.btn_save_question);


        btnAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addQuestion();
            }
        });

        btnSaveQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveQuestion();
            }
        });

    }

    private void saveQuestion() {
        if (totalExamMark > totalQuestionMark || totalExamMark == totalQuestionMark){
            Toast.makeText(QuestionSetActivity.this, "Please Add More Question", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void addQuestion() {
        question = edtQuestion.getText().toString().trim();

        optionOne = edtOptionOne.getText().toString().trim();
        optionTwo = edtOptionTwo.getText().toString().trim();
        optionThree = edtOptionThree.getText().toString().trim();
        optionFour = edtOptionFour.getText().toString().trim();

        int radioMarkId = radioMarkGrp.getCheckedRadioButtonId();

        boolean isCheckedOne = checkOne.isChecked();
        boolean isCheckedTwo = checkTwo.isChecked();
        boolean isCheckedThree = checkThree.isChecked();
        boolean isCheckedFour = checkFour.isChecked();

        if (question.isEmpty()){
            edtValidator(edtQuestion, "Please, Enter question.");
            return;
        }
        if (optionOne.isEmpty()){
            edtValidator(edtOptionOne, "Option Can't be Empty");
            return;
        }
        if (optionTwo.isEmpty()){
            edtValidator(edtOptionTwo, "Option Can't be Empty");
            return;
        }
        if (optionThree.isEmpty()){
            edtValidator(edtOptionThree, "Option Can't be Empty");
            return;
        }
        if (optionFour.isEmpty()){
            edtValidator(edtOptionFour, "Option Can't be Empty");
            return;
        }

        if (!isCheckedOne && !isCheckedTwo && !isCheckedThree && !isCheckedFour){
            Toast.makeText(QuestionSetActivity.this, "Select Correct Option", Toast.LENGTH_SHORT).show();
            radioAnsGrp.requestFocus();
            return;
        } else {
            int radioAnsID = radioAnsGrp.getCheckedRadioButtonId();

            if (radioAnsID == R.id.checkbox_one){
                correctAns = optionOne;
            } else if (radioAnsID == R.id.checkbox_two) {
                correctAns = optionTwo;
            } else if (radioAnsID == R.id.checkbox_three) {
                correctAns = optionThree;
            } else if (radioAnsID == R.id.checkbox_four) {
                correctAns = optionFour;
            }
        }

        if (radioMarkId == R.id.q_mark_one){
            questionMark = Integer.parseInt(markOne.getText().toString());
        } else if (radioMarkId == R.id.q_mark_two) {
            questionMark = Integer.parseInt(markTwo.getText().toString());
        } else if (radioMarkId == R.id.q_mark_three) {
            questionMark = Integer.parseInt(markThree.getText().toString());
        }

        questionModelList.add(new QuestionModel(question, optionOne, optionTwo,
                optionThree, optionFour, correctAns, questionMark));

        totalQuestionMark += questionMark;
        if (totalQuestionMark < 10){
            txtQuestionTotalMarks.setText("0" + totalQuestionMark);
        } else {
            txtQuestionTotalMarks.setText(totalQuestionMark);
        }

        questionNo++;
        if (questionNo < 10){
            txtQuestionNo.setText("Question No : 0" + questionNo);
        } else {
            txtQuestionNo.setText("Question No : " + questionNo);
        }

        edtQuestion.setText("");
        edtOptionOne.setText("");
        edtOptionTwo.setText("");
        edtOptionThree.setText("");
        edtOptionFour.setText("");

        radioAnsGrp.clearCheck();
        markOne.setChecked(true);
    }

    private void edtValidator(EditText editText, String message){
        editText.setError(message);
        editText.requestFocus();
    }
}