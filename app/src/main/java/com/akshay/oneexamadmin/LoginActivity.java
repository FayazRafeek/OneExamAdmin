package com.akshay.oneexamadmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.akshay.oneexamadmin.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {


    ActivityLoginBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLogin();
            }
        });
    }


    void startLogin(){

        String staffId = binding.staffIdInp.getText().toString();
        String passcode = binding.pssInp.getText().toString();


        binding.loginBtn.setEnabled(false);
        binding.loginProgress.setVisibility(View.VISIBLE);
        FirebaseFirestore.getInstance()
                .collection("Staffs")
                .document(staffId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        binding.loginBtn.setEnabled(true);
                        binding.loginProgress.setVisibility(View.GONE);

                        if(task.isSuccessful()){
                            if(task.getResult().exists()){
                                Staff staff = task.getResult().toObject(Staff.class);
                                if(staff.getPasscode().equals(passcode)){
                                    saveStaff(staff);
                                    Toast.makeText(LoginActivity.this, "Login successfull", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "No admin exist for this credentials", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void saveStaff(Staff staff){

        SharedPreferences sharedPreferences = getSharedPreferences("ONEXAM_ADMIN",MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(staff);
        editor.putString("STAFF_DATA",json);
        editor.putBoolean("IS_LOGIN", true);
        editor.commit();
    }
}
