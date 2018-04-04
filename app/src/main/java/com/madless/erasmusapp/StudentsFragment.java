package com.madless.erasmusapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class StudentsFragment extends android.support.v4.app.Fragment {

    View v;
    private RecyclerView recyclerView;
    private List<Student> listStudent;

    public StudentsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.students_fragment, container, false);
        recyclerView = v.findViewById(R.id.students_recycler);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), listStudent);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listStudent = new ArrayList<>();
        listStudent.add(new Student("Dominika Gasparova", "0905xxxxxx"));
        listStudent.add(new Student("Martin Pribilovic", "0905xxxxxx"));
        listStudent.add(new Student("Tomas Cellar", "0905xxxxxx"));
    }

}
