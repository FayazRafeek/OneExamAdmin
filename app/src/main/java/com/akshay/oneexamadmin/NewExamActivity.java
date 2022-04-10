package com.akshay.oneexamadmin;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.akshay.oneexamadmin.databinding.ActivityNewexamBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class NewExamActivity extends AppCompatActivity implements QuesAddAdapter.QuesAction, SelectDialog.OnDialogSelect {


    ActivityNewexamBinding binding;
    MainViewModel mainViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        binding = ActivityNewexamBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.addQuestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new QuestionAddDialog()
                        .show(getSupportFragmentManager(),"TAG");
            }
        });

        mainViewModel.live.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                switch (s){
                    case "QUESTION_UPDATE" :
                        updateQuesRecycler();
                }
            }
        });

        binding.programInp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgramDialog();
            }
        });

        binding.semInp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSemDialog();
            }
        });

        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gatherData();
            }
        });
    }


    void gatherData(){

        String examName = binding.examName.getText().toString();
        String program = binding.programInp.getText().toString();
        String sem = binding.semInp.getText().toString();

        String staffSt =  getSharedPreferences("ONEXAM_ADMIN",MODE_PRIVATE).getString("STAFF_DATA","");
        Staff staff = new Gson().fromJson(staffSt,Staff.class);


        Exam exam = new Exam(String.valueOf(System.currentTimeMillis()),examName,program,sem,mainViewModel.getQuestionsCreated());

        exam.setStaffId(staff.getStaffId());
        exam.setStaffName(staff.getStaffName());
        binding.programInp.setVisibility(View.VISIBLE);

        FirebaseFirestore
                .getInstance().collection("Exams")
                .document(exam.getExamId())
                .set(exam)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        binding.examProgress.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            Toast.makeText(NewExamActivity.this, "Exam added successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(NewExamActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    QuesAddAdapter quesAddAdapter;
    void updateQuesRecycler(){

        if(quesAddAdapter == null){
            quesAddAdapter = new QuesAddAdapter(this,this);
            binding.quesRecycler.setAdapter(quesAddAdapter);
            binding.quesRecycler.setLayoutManager(new LinearLayoutManager(this));
        }

        quesAddAdapter.updateList(mainViewModel.getQuestionsCreated());
    }

    @Override
    public void deleteQuest(Question question) {
        mainViewModel.removeQuest(question);
    }

    void showProgramDialog(){
        List<String> programs = new ArrayList<>();
        programs.add("BA English");programs.add("BA Hindi");programs.add("Bsc Chemistry");programs.add("BCA");
        new SelectDialog(programs,"Select Program : ",this,"PROGRAM").show(getSupportFragmentManager(),"TAG");
    }
    void showSemDialog(){
        List<String> sems = new ArrayList<>();
        sems.add("First Sem");sems.add("Second Sem");sems.add("Third Sem");sems.add("Fourth Sem");
        new SelectDialog(sems,"Select Semester : ",this,"SEMESTER").show(getSupportFragmentManager(),"TAG");
    }

    String program, sem;
    @Override
    public void onSelectItem(String item, String tag) {
        if(tag.equals("PROGRAM")){
            program = item;
            binding.programInp.setText(item);
        }
        else{
            sem = item;
            binding.semInp.setText(sem);
        }
    }
}
