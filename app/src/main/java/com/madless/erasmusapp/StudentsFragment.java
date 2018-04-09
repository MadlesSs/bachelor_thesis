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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
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
    private List<Integer> checkedStudentIds;
    private List<Student> registeredStudents;
    private View customToastLayout;
    private int tripId;
    private ImageView customToastImg;
    private TextView customToastTxt;
    private Toast customToast;
    private int imgYes;
    private int imgNo;

    public StudentsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.students_fragment, container, false);
        recyclerView = v.findViewById(R.id.students_recycler);
        tripId = Integer.parseInt(getArguments().getString("id"));
        Log.d("era", "onCreateView: tripId " + tripId);
        databaseTrips.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                checkedStudentIds.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    DataItem item = child.getValue(DataItem.class);
                    if (item.id == tripId) {
                        studentChecks = item.studentCheck;
                        break;
                    }
                }
                for (StudentCheck check : studentChecks) {
                    Log.d("era", "onDataChange: " + check.getId());
                    if (check.isChecked()) {
                        checkedStudentIds.add(check.getId());
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
                        if (check.getId() == student.getId()) {
                            registeredStudents.add(student);
                            if (!check.isChecked()) {
                                listStudent.add(student);
                            } else {
                                checkedStudentIds.add(check.getId());
                            }
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        customToastLayout = getLayoutInflater().inflate(R.layout.custom_toast,
                view.findViewById(R.id.toast_layout_root));
        customToastImg = customToastLayout.findViewById(R.id.toast_image);
        customToastTxt = customToastLayout.findViewById(R.id.toast_text);
        customToast = new Toast(getActivity().getApplicationContext());
        customToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        customToast.setDuration(Toast.LENGTH_SHORT);
        customToast.setView(customToastLayout);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseStudents = FirebaseDatabase.getInstance().getReference("students");
        databaseTrips = FirebaseDatabase.getInstance().getReference("trips");
        mZXingScannerView = new ZXingScannerView(getContext());
        listStudent = new ArrayList<>();
        checkedStudentIds = new ArrayList<>();
        registeredStudents = new ArrayList<>();
        imgYes = R.drawable.yes;
        imgNo = R.drawable.no;

    }

    @Override
    public void handleResult(Result result) {
        boolean found = false;
        String number = result.getText();
        for (Student student : listStudent) {
            if (student.getNumber().equals(number)) {
                // student registered for a trip and not checked yet
                markStudentChecked(student.getId());
                customToastImg.setImageResource(imgYes);
                customToastTxt.setText("Code scanned succesfully.");
                found = true;
                break;
            }
        }
        for (Student student : registeredStudents) {
            if (student.getNumber().equals(number)
                    && checkedStudentIds.contains(student.getId())) {
                customToastImg.setImageResource(imgNo);
                customToastTxt.setText("QR code scanned already.");
                found = true;
                break;
            }
        }
        if (!found) {
            customToastImg.setImageResource(imgNo);
            customToastTxt.setText("User not registered.");
        }
        customToast.show();

        Intent intent = new Intent(getContext(), StudentsList.class);
        intent.putExtra("id", String.valueOf(tripId));
        Log.d("era", "handleResult: tripId " + tripId);
        getActivity().finish();
        getContext().startActivity(intent);

    }

    public void markStudentChecked(int id) {

        final String[] key = {""};
        databaseTrips.child(String.valueOf(tripId)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot check : dataSnapshot.child("studentCheck").getChildren()) {
                    Log.d("era", "markStudentChecked: " + (long) check.child("id").getValue());
                    if ((long) check.child("id").getValue() == id) {
                        Log.d("era", "markign student on key" + check.getKey());
                        databaseTrips.child(String.valueOf(tripId)).child("studentCheck")
                                .child(String.valueOf(check.getKey())).child("checked").setValue(true);
                        //key[0] = String.valueOf(check.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.d("era", "markStudentChecked: checking! " + key[0] );

    }

}
