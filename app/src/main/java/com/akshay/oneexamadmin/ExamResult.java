package com.akshay.oneexamadmin;

public class ExamResult {

    String resultId;
    String examId, userId, staffId, staffName, userName;
    String examName;
    String program,sem;
    int totalQuestions, score;
    String timestamp;

    public ExamResult() {
    }

    public ExamResult(String resultId, String examId, String userId, String staffId, String staffName, String examName, String program, String sem, int totalQuestions, int score, String timestamp) {
        this.resultId = resultId;
        this.examId = examId;
        this.userId = userId;
        this.staffId = staffId;
        this.staffName = staffName;
        this.examName = examName;
        this.program = program;
        this.sem = sem;
        this.totalQuestions = totalQuestions;
        this.score = score;
        this.timestamp = timestamp;
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
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

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
