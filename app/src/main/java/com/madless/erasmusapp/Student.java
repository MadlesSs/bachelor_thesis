package com.madless.erasmusapp;

public class Student {

    private int id;
    private String name;
    private String number;
    private boolean checked;

    public Student() {
        this.checked = false;
    }

    public Student(int id, String name, String number, boolean checked) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
