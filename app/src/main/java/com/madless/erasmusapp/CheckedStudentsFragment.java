package com.madless.erasmusapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CheckedStudentsFragment extends Fragment {
    View v;
    private RecyclerView recyclerView;
    private List<Student> listCheckedStudents;
    public CheckedStudentsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.checked_students_fragment, container, false);
        recyclerView = v.findViewById(R.id.checked_students_recycler);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), listCheckedStudents);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listCheckedStudents = new ArrayList<>();
        listCheckedStudents.add(new Student("Dominika Gasparova", "0905xxxxxx"));
        listCheckedStudents.add(new Student("Tomas Cellar", "0905xxxxxx"));
    }
}
