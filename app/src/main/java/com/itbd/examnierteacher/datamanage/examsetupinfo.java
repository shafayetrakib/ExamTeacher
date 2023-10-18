package com.itbd.examnierteacher.datamanage;

public class examsetupinfo {
    private String ExamsetupName;
    private String ExamsetupSyllabus;
    private String ExamsetupDate;
    private String ExamsetupTime;
    private String ExamsetupMark;
    private String ExamsetupDuration;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public examsetupinfo() {
    }

    public examsetupinfo(String examsetupName, String examsetupSyllabus, String examsetupDate, String examsetupTime, String examsetupMark, String examsetupDuration) {
        ExamsetupName = examsetupName;
        ExamsetupSyllabus = examsetupSyllabus;
        ExamsetupDate = examsetupDate;
        ExamsetupTime = examsetupTime;
        ExamsetupMark = examsetupMark;
        ExamsetupDuration = examsetupDuration;
    }

    public String getExamsetupName() {
        return ExamsetupName;
    }

    public void setExamsetupName(String examsetupName) {
        ExamsetupName = examsetupName;
    }

    public String getExamsetupSyllabus() {
        return ExamsetupSyllabus;
    }

    public void setExamsetupSyllabus(String examsetupSyllabus) {
        ExamsetupSyllabus = examsetupSyllabus;
    }

    public String getExamsetupDate() {
        return ExamsetupDate;
    }

    public void setExamsetupDate(String examsetupDate) {
        ExamsetupDate = examsetupDate;
    }

    public String getExamsetupTime() {
        return ExamsetupTime;
    }

    public void setExamsetupTime(String examsetupTime) {
        ExamsetupTime = examsetupTime;
    }

    public String getExamsetupMark() {
        return ExamsetupMark;
    }

    public void setExamsetupMark(String examsetupMark) {
        ExamsetupMark = examsetupMark;
    }

    public String getExamsetupDuration() {
        return ExamsetupDuration;
    }

    public void setExamsetupDuration(String examsetupDuration) {
        ExamsetupDuration = examsetupDuration;
    }
}
