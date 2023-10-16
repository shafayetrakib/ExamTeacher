package com.itbd.examnierteacher.datamanage;

public class signupInfo {
    private  String Fullname;
    private String Email;
    private String Phone;
    private String Course;

    public signupInfo() {
    }

    public signupInfo(String fullname, String email, String phone, String course) {
        Fullname = fullname;
        Email = email;
        Phone = phone;
        Course = course;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getCourse() {
        return Course;
    }

    public void setCourse(String course) {
        Course = course;
    }
}
