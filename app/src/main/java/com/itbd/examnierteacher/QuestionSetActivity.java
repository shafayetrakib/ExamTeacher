package com.itbd.examnierteacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itbd.examnierteacher.DataMoldes.QuestionModel;

import java.util.ArrayList;
import java.util.List;

public class QuestionSetActivity extends AppCompatActivity {

    EditText questionNumber, writeQuestion, optionOne, optionTwo, optionThree, optionFour;
    Button add, submit;
    DatabaseReference databaseReference;
    RadioButton chOne, chTwo, chThree, chFour;
    TextView correctAnswer;
    List<QuestionModel> questionModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        questionNumber = findViewById(R.id.question_number);
        writeQuestion = findViewById(R.id.edt_writequestion);
        optionOne = findViewById(R.id.edt_optionone);
        optionTwo = findViewById(R.id.edt_optiontwo);
        optionThree = findViewById(R.id.edt_optionthree);
        optionFour = findViewById(R.id.edt_optionfour);
        add = findViewById(R.id.btn_questionadd);
        submit = findViewById(R.id.btn_submitquestion);

        getWindow().setStatusBarColor(ContextCompat.getColor(QuestionSetActivity.this, R.color.blue_pr));

        databaseReference = FirebaseDatabase.getInstance().getReference();

        //For Current Answer checkbox
        chOne = findViewById(R.id.checkbox_one);
        chTwo = findViewById(R.id.checkbox_two);
        chThree = findViewById(R.id.checkbox_three);
        chFour = findViewById(R.id.checkbox_four);


        // for add question
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                questionSet();
            }
        });

        //for submit question
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(QuestionSetActivity.this, DashboardActivity.class));
                finish();
            }
        });

    }

    public void questionSet() {

        String WriteQuestion = writeQuestion.getText().toString().trim();
        String OptionOne = optionOne.getText().toString().trim();
        String OptionTwo = optionTwo.getText().toString().trim();
        String OptionThree = optionThree.getText().toString().trim();
        String OptionFour = optionFour.getText().toString().trim();
        String checkOne = chOne.getText().toString();
        String checkTwo = chTwo.getText().toString();
        String checkThree = chThree.getText().toString();
        String checkFour = chFour.getText().toString();
        String Correct = correctAnswer.getText().toString();

        getWindow().setStatusBarColor(ContextCompat.getColor(QuestionSetActivity.this, R.color.blue_pr));

        // for checkbox

        if (chOne.isChecked()) {
            optionOne.getText().toString();
            correctAnswer.setText(OptionOne);
        }
        if (chTwo.isChecked()) {
            optionTwo.getText().toString().trim();
            correctAnswer.setText(OptionTwo);
        }
        if (chThree.isChecked()) {
            optionThree.getText().toString().trim();
            correctAnswer.setText(OptionThree);

        }
        if (chFour.isChecked()) {
            optionFour.getText().toString().trim();
            correctAnswer.setText(OptionFour);
        }

//end checkbox
        if (TextUtils.isEmpty(WriteQuestion)) {
            writeQuestion.setError("please write the question");
            writeQuestion.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(OptionOne)) {
            optionOne.setError("don't set option one");
            optionOne.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(OptionTwo)) {
            optionTwo.setError("don't set option two");
            optionTwo.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(OptionThree)) {
            optionThree.setError("don't set option three");
            optionThree.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(OptionFour)) {
            optionFour.setError("don't set option four");
            optionFour.requestFocus();
            return;
        }

        questionModelList.add(new QuestionModel());

        writeQuestion.setText("");
        optionOne.setText("");
        optionTwo.setText("");
        optionThree.setText("");
        optionFour.setText("");



        Toast.makeText(this, "question succefully set", Toast.LENGTH_SHORT).show();
    }


}