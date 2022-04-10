package com.akshay.oneexamadmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.akshay.oneexamadmin.databinding.ActivityProfileBinding;
import com.google.gson.Gson;

public class ProfileActivity extends AppCompatActivity {


    ActivityProfileBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String staffSt =  getSharedPreferences("ONEXAM_ADMIN",MODE_PRIVATE).getString("STAFF_DATA","");
        Staff staff = new Gson().fromJson(staffSt,Staff.class);

        binding.userName.setText(staff.getStaffName());
        binding.staffId.setText(staff.getStaffId());
        binding.phone.setText(staff.getPhone());
        binding.passcode.setText(staff.getPasscode());

        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("ONEXAM_ADMIN",MODE_PRIVATE);
                SharedPreferences.Editor editor= sharedPreferences.edit();

                editor.putString("STAFF_DATA",null);
                editor.putBoolean("IS_LOGIN", false);
                editor.commit();

                Intent intent = new Intent(ProfileActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}
