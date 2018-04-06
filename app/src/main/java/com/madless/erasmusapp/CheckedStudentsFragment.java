package com.madless.erasmusapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CheckedStudentsFragment extends Fragment {
    View v;
    private RecyclerView recyclerView;
    private List<Student> listCheckedStudents;
    private DatabaseReference databaseStudents;
    private DatabaseReference databaseTrips;
    private List<StudentCheck> studentChecks;
    public CheckedStudentsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.checked_students_fragment, container, false);
        recyclerView = v.findViewById(R.id.checked_students_recycler);
        int id = Integer.parseInt(getArguments().getString("id"));

        databaseTrips.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    DataItem item = child.getValue(DataItem.class);
                    if (item.id == id) {
                        studentChecks = item.studentCheck;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseStudents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listCheckedStudents.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Student student = child.getValue(Student.class);
                    for (StudentCheck check : studentChecks) {
                        if (check.getId() == student.getId() && check.isChecked()) {
                            listCheckedStudents.add(student);
                        }
                    }
                }
                RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), listCheckedStudents);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseStudents = FirebaseDatabase.getInstance().getReference("students");
        databaseTrips = FirebaseDatabase.getInstance().getReference("trips");
        listCheckedStudents = new ArrayList<>();
    }
}
