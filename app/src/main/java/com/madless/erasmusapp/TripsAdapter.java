package com.madless.erasmusapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    List<DataItem> data = Collections.emptyList();
    private Context mContext;

    public TripsAdapter(Context context, List<DataItem> data) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = inflater.inflate(R.layout.trip_row, parent, false);
       MyViewHolder holder = new MyViewHolder(view);
       holder.rowTrip.setOnClickListener(view1 -> {
           Intent intent = new Intent(mContext, StudentsList.class);
           int id = data.get(holder.getAdapterPosition()).id;
           intent.putExtra("id", String.valueOf(id));
           mContext.startActivity(intent);
       });
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
        LinearLayout rowTrip;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.trip_text);
            rowTrip = itemView.findViewById(R.id.trip_row_id);
        }
    }
}
