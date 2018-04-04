package com.madless.erasmusapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
        mZXingScannerView = new ZXingScannerView(getContext());
        listStudent = new ArrayList<>();
        listStudent.add(new Student("Dominika Gasparova", "0905xxxxxx"));
        listStudent.add(new Student("Martin Pribilovic", "0905xxxxxx"));
        listStudent.add(new Student("Tomas Cellar", "0905xxxxxx"));
    }

    @Override
    public void handleResult(Result result) {
        Toast.makeText(getContext(), result.getText(), Toast.LENGTH_SHORT).show();
        mZXingScannerView.resumeCameraPreview(this);
    }
}
