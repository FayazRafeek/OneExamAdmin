package com.akshay.oneexamadmin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.akshay.oneexamadmin.databinding.ActivityExamAttemptsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ExamAttemptsActivity extends AppCompatActivity implements ExamListAdapter.ExamListClick {


    ActivityExamAttemptsBinding binding;
    Exam exam;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityExamAttemptsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        exam = AppSingleton.getInstance().getSelectedExam();
        if(exam == null ) finish();

        binding.examName.setText(exam.getExamName());

        fetchAttempts();

        binding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchAttempts();
            }
        });
    }

    void fetchAttempts(){

        binding.swipe.setRefreshing(true);
        FirebaseFirestore.getInstance()
                .collection("Exams")
                .document(exam.getExamId())
                .collection("Attempts")
                .orderBy("timestamp")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        binding.swipe.setRefreshing(false);
                        if(task.isSuccessful()){

                            List<ExamResult> examResults = new ArrayList<>();
                            for(DocumentSnapshot doc : task.getResult()){
                                ExamResult item = doc.toObject(ExamResult.class);
                                examResults.add(item);
                            }

                            updateRecycler(examResults);
                        }
                    }
                });
    }

    ExamListAdapter examListAdapter;
    void updateRecycler(List<ExamResult> list){

        if(examListAdapter == null){
            examListAdapter = new ExamListAdapter(this,this,"RESULT");
            binding.attemptRecycler.setAdapter(examListAdapter);
            binding.attemptRecycler.setLayoutManager(new LinearLayoutManager(this));
        }

        examListAdapter.updateResultList(list);
    }

    @Override
    public void onExamItemClick(Exam exam) {

    }

    @Override
    public void onResultItemClick(ExamResult examResult) {
        AppSingleton.getInstance().setSelectedResult(examResult);
        Intent intent = new Intent(this, ExamDetailActivity.class);
        intent.putExtra("TYPE","EXAM_RESULT");
        startActivity(intent);
    }
}
