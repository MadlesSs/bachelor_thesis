package com.madless.erasmusapp;


import java.util.ArrayList;
import java.util.List;

public class DataItem {
    String title;
    List<StudentCheck> studentCheck;
    int id;

    public DataItem() {
        studentCheck = new ArrayList<>();
    }

    public DataItem(String title, ArrayList<StudentCheck> studentCheck, int id) {
        this.title = title;
        this.studentCheck = studentCheck;
        this.id = id;
    }
}
