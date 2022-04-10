package com.akshay.oneexamadmin;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {

    public MainViewModel() {

    }

    public MutableLiveData<String> live = new MutableLiveData<>();

    public LiveData<String> getLive() {
        return live;
    }

    public void setLive(String live) {
        this.live.setValue(live);
    }

    List<Question> questionsCreated = new ArrayList<>();
    public void addQuestion(Question question){
        questionsCreated.add(question);
        live.setValue("QUESTION_UPDATE");
    }

    public void removeQuest(Question question){
        questionsCreated.remove(question);
        live.setValue("QUESTION_UPDATE");
    }

    public ArrayList<Question> getQuestionsCreated() {
        return new ArrayList<>(questionsCreated);
    }
}
