package com.akshay.oneexamadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.akshay.oneexamadmin.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ExamListAdapter.ExamListClick {

    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.nexExmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,NewExamActivity.class));
            }
        });

        binding.profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
            }
        });


        fetchExams();

        binding.mainSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchExams();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchExams();
    }

    void fetchExams(){


        binding.mainSwipe.setRefreshing(true);
        String staffSt =  getSharedPreferences("ONEXAM_ADMIN",MODE_PRIVATE).getString("STAFF_DATA","");
        Staff staff = new Gson().fromJson(staffSt,Staff.class);

        FirebaseFirestore.getInstance()
                .collection("Exams")
                .whereEqualTo("staffId", staff.getStaffId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        Log.d("333", "onComplete: Exmas " + task.getResult().size());
                        binding.mainSwipe.setRefreshing(false);
                        if(task.isSuccessful()){

                            List<Exam> list = new ArrayList<>();
                            for(DocumentSnapshot doc : task.getResult()){
                                Exam item = doc.toObject(Exam.class);
                                list.add(item);
                            }

                            updateRecycler(list);
                        }
                    }
                });
    }

    ExamListAdapter examListAdapter;
    void updateRecycler(List<Exam> exams){
        if(examListAdapter == null){
            examListAdapter = new ExamListAdapter(this,this);
            binding.mainRecycler.setAdapter(examListAdapter);
            binding.mainRecycler.setLayoutManager(new LinearLayoutManager(this));
        }
        examListAdapter.updateList(exams);
    }

    @Override
    public void onExamItemClick(Exam exam) {

        AppSingleton.getInstance().setSelectedExam(exam);
        startActivity(new Intent(this, ExamDetailActivity.class));
    }

    @Override
    public void onResultItemClick(ExamResult examResult) {

    }
}