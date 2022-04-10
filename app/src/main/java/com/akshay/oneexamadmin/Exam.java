package com.akshay.oneexamadmin;

import java.util.ArrayList;
import java.util.List;

public class Exam {
    String examId, examName, program,sem;
    ArrayList<Question> questions;
    String staffId, staffName;

    public Exam() {
    }

    public Exam(String examId, String examName, String program, String sem, ArrayList<Question> questions) {
        this.examId = examId;
        this.examName = examName;
        this.program = program;
        this.sem = sem;
        this.questions = questions;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }


    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }
}
