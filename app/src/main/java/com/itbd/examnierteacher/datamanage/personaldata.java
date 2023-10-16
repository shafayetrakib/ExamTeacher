package com.itbd.examnierteacher.datamanage;

public class personaldata {
    private String Fullname;
    private String Email;
    private String Phone;
    private String PermanentAdd;
    private String PresentAdd;

    public personaldata() {
    }

    public personaldata(String fullname, String email, String phone, String permanentAdd, String presentAdd) {
        Fullname = fullname;
        Email = email;
        Phone = phone;
        PermanentAdd = permanentAdd;
        PresentAdd = presentAdd;
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

    public String getPermanentAdd() {
        return PermanentAdd;
    }

    public void setPermanentAdd(String permanentAdd) {
        PermanentAdd = permanentAdd;
    }

    public String getPresentAdd() {
        return PresentAdd;
    }

    public void setPresentAdd(String presentAdd) {
        PresentAdd = presentAdd;
    }
}
