package com.madless.erasmusapp;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TripsFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference databaseTrips;
    private List<DataItem> listTrips;
    private ProgressDialog dialog;

    TripsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseTrips = FirebaseDatabase.getInstance().getReference("trips");
        listTrips = new ArrayList<>();
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("loading trips");
        dialog.show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trips_fragment, container, false);
        databaseTrips.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listTrips.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    DataItem item = child.getValue(DataItem.class);
                    listTrips.add(item);
                }
                adapter = new TripsAdapter(getActivity(), listTrips);
                recyclerView = view.findViewById(R.id.tripsRecyclerView);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                dialog.hide();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }
}
