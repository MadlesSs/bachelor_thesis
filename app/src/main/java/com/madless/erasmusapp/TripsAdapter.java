package com.madless.erasmusapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    List<DataItem> data = Collections.emptyList();

    public TripsAdapter(Context context, List<DataItem> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = inflater.inflate(R.layout.trip_row, parent, false);
       MyViewHolder holder = new MyViewHolder(view);
       return holder;
    }

    @Override
    public void onBindViewHolder(TripsAdapter.MyViewHolder holder, int position) {
        DataItem current = data.get(position);
        holder.title.setText(current.title);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.trip_text);
        }
    }
}
