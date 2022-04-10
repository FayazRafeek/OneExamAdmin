package com.akshay.oneexamadmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.akshay.oneexamadmin.databinding.DialogQuestAddBinding;

import java.util.ArrayList;
import java.util.List;

public class QuestionAddDialog extends DialogFragment {


    DialogQuestAddBinding binding;
    MainViewModel mainViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogQuestAddBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gatherData();

                dismiss();
            }
        });


    }


    void gatherData(){

        String quest = binding.questInp.getText().toString();

        String op1 = binding.op1.getText().toString();
        String op2 = binding.op2.getText().toString();
        String op3 = binding.op3.getText().toString();
        String op4 = binding.op4.getText().toString();



        if(binding.op1IsAnswer.isChecked())
            answerOption = op1;
        if(binding.op2IsAnswer.isChecked())
            answerOption = op2;
        if(binding.op3IsAnswer.isChecked())
            answerOption = op3;
        if(binding.op4IsAnswer.isChecked())
            answerOption = op4;

        Question question = new Question(quest,op1,op2,op3,op4,answerOption);

        int time = Integer.valueOf(binding.timeInp.getText().toString());
        question.setTime(time);
        mainViewModel.addQuestion(question);

        dismiss();
    }


    String answerOption = "";
}
