package com.akshay.oneexamadmin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.akshay.oneexamadmin.databinding.ActivityExamDetailBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ExamDetailActivity extends AppCompatActivity {

    ActivityExamDetailBinding binding;
    Exam exam; ExamResult examResult;

    String type = "EXAM_DETAIL";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityExamDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        type = getIntent().getStringExtra("TYPE");
        if(type == null) type = "EXAM_DETAIL";

        if(type.equals("EXAM_DETAIL")){
            exam = AppSingleton.getInstance().getSelectedExam();
            if(exam == null) finish();
            updateExamUi();

            binding.attemptBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(ExamDetailActivity.this, ExamAttemptsActivity.class));
                }
            });
        } else if (type.equals("EXAM_RESULT")){
            examResult = AppSingleton.getInstance().getSelectedResult();
            if(examResult == null) finish();
            updateResultUi();
        }


        binding.closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(deleteConfirmCount == 0){
                    Toast.makeText(ExamDetailActivity.this, "Click again to confirm", Toast.LENGTH_SHORT).show();
                    deleteConfirmCount++;

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            deleteConfirmCount = 0;
                        }
                    },3000);
                } else
                    deleteExam();
            }
        });
    }
    int deleteConfirmCount = 0;

    private static final String TAG = "333";
    void updateExamUi(){

        binding.examName.setText(exam.getExamName());
        binding.examPrgm.setText(exam.getProgram());
        binding.examSem.setText(exam.getSem());
        binding.examQuesCount.setText(exam.getQuestions().size() + "");

        Log.d(TAG, "updateUi: Questions " + exam.getQuestions());

    }

    void updateResultUi(){

        binding.heroText.setText("Exam Result");
        binding.examName.setText(examResult.getExamName());
        binding.prgrmParent.setVisibility(View.GONE);
        binding.semParent.setVisibility(View.GONE);
        binding.quesParent.setVisibility(View.GONE);

        binding.attemptParent.setVisibility(View.VISIBLE);
        binding.scoreParent.setVisibility(View.VISIBLE);
        binding.saScore.setText(examResult.getScore() + " out of " + examResult.getTotalQuestions());

        Date date = new Date();
        date.setTime(Long.parseLong(examResult.getTimestamp()));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateSt = dateFormat.format(calendar.getTime());
        binding.examResultAttemtDate.setText(dateSt);

        binding.attemptBtn.setVisibility(View.GONE);
        binding.deleteBtn.setVisibility(View.GONE);

        binding.usernameParent.setVisibility(View.VISIBLE);
        binding.userName.setText(examResult.getUserName());
        binding.userId.setText(examResult.getUserId());
        binding.userId.setVisibility(View.VISIBLE);



    }

    void deleteExam(){

        FirebaseFirestore.getInstance()
                .collection("Exams")
                .document(exam.getExamId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ExamDetailActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }
}
