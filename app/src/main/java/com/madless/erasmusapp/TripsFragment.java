package com.madless.erasmusapp;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TripsFragment extends Fragment {

    private RecyclerView recyclerView;

    TripsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trips_fragment, container, false);
        adapter = new TripsAdapter(getActivity(), getData());
        recyclerView = view.findViewById(R.id.tripsRecyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    public static List<DataItem> getData() {
        List<DataItem> data = new ArrayList<>();
        String[] titles = {"Vienna", "Warsaw", "Rome"};
        for(String title : titles) {
            DataItem current = new DataItem();
            current.title = title;
            data.add(current);
        }
        return data;
    }
}
