package com.akshay.oneexamadmin;

public class AppSingleton {

    public static AppSingleton INSTANCE = null;
    public static AppSingleton getInstance(){
        if(INSTANCE == null) INSTANCE = new AppSingleton();
        return INSTANCE;
    }

    Exam selectedExam;

    public Exam getSelectedExam() {
        return selectedExam;
    }

    public void setSelectedExam(Exam selectedExam) {
        this.selectedExam = selectedExam;
    }

    ExamResult selectedResult;

    public ExamResult getSelectedResult() {
        return selectedResult;
    }

    public void setSelectedResult(ExamResult selectedResult) {
        this.selectedResult = selectedResult;
    }
}
