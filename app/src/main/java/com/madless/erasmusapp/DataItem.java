package com.madless.erasmusapp;


import java.util.ArrayList;
import java.util.List;

public class DataItem {
    String title;
    List<Integer> studentids;
    int id;

    public DataItem() {
        studentids = new ArrayList<>();
    }

    public DataItem(String title, ArrayList<Integer> students, int id) {
        this.title = title;
        this.studentids = students;
        this.id = id;
    }
}
