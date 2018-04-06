package com.madless.erasmusapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;
import com.robertlevonyan.views.customfloatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class StudentsFragment extends android.support.v4.app.Fragment implements ZXingScannerView.ResultHandler{

    View v;
    private RecyclerView recyclerView;
    private List<Student> listStudent;
    private FloatingActionButton btnFab;
    final int REQUEST_CODE = 123;
    private ZXingScannerView mZXingScannerView;
    private DatabaseReference databaseStudents;
    private DatabaseReference databaseTrips;
    private List<StudentCheck> studentChecks;
    private int tripId;

    public StudentsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.students_fragment, container, false);
        recyclerView = v.findViewById(R.id.students_recycler);
        int tripId = Integer.parseInt(getArguments().getString("id"));
        Log.d("era", "onCreateView: " + tripId);

        databaseTrips.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    DataItem item = child.getValue(DataItem.class);
                    if (item.id == tripId) {
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
                listStudent.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Student student = child.getValue(Student.class);
                    for (StudentCheck check : studentChecks) {
                        if (check.getId() == student.getId() && !check.isChecked()) {
                            listStudent.add(student);
                        }
                    }
                }
                RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), listStudent);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        btnFab = v.findViewById(R.id.custom_fab);
        btnFab.setOnClickListener(view -> {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[] {Manifest.permission.CAMERA}, REQUEST_CODE);
                return;
            }
            getActivity().setContentView(mZXingScannerView);
            mZXingScannerView.setResultHandler(this);
            mZXingScannerView.startCamera();
        });
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseStudents = FirebaseDatabase.getInstance().getReference("students");
        databaseTrips = FirebaseDatabase.getInstance().getReference("trips");
        mZXingScannerView = new ZXingScannerView(getContext());
        listStudent = new ArrayList<>();

    }

    @Override
    public void handleResult(Result result) {
        for (Student student : listStudent) {
            if (student.getNumber().equals(result.getText())) {
                markStudentChecked(student.getId());
            }
        }
        Toast.makeText(getContext(), result.getText(), Toast.LENGTH_SHORT).show();
        mZXingScannerView.resumeCameraPreview(this);
        getActivity().finish();
        Intent intent = new Intent(getContext(), StudentsList.class);
        intent.putExtra("id", String.valueOf(tripId));
        getContext().startActivity(intent);
    }

    public void markStudentChecked(int id) {
        databaseTrips.child(String.valueOf(tripId)).child("studentCheck")
                .child(String.valueOf(id)).child("checked").setValue(true);
    }

}
