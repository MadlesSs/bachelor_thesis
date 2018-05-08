package com.madless.erasmusapp;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    final int REQUEST_CALL = 111;
    Context mContext;
    List<Student> mData;
    Dialog myDialog;

    public RecyclerViewAdapter(Context mContext, List<Student> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.student_row, parent, false);
        MyViewHolder vHolder = new MyViewHolder(view);

        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.dialog_contact);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        vHolder.rowStudent.setOnClickListener(view1 -> {
            TextView dialog_name = myDialog.findViewById(R.id.dialog_name);
            TextView dialog_number = myDialog.findViewById(R.id.dialog_number);
            dialog_name.setText(mData.get(vHolder.getAdapterPosition()).getName());
            dialog_number.setText(mData.get(vHolder.getAdapterPosition()).getNumber());
            Button btnCall = myDialog.findViewById(R.id.dialog_btn_call);
            Button btnSMS = myDialog.findViewById(R.id.dialog_btn_message);
            btnCall.setOnClickListener(view2 -> {
                Intent intent = new Intent(Intent.ACTION_CALL,
                        Uri.parse("tel:" + dialog_number.getText().toString()));
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE)
                                            != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mContext.startActivity(intent);
            });
            btnSMS.setOnClickListener(view3 -> {
                String body = "Hi " + dialog_name.getText().toString() + ". Are you coming on the " +
                        "trip? We are waiting for you";
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:" + dialog_number.getText().toString()));
                sendIntent.putExtra("sms_body",body);
                mContext.startActivity(sendIntent);
            });

            myDialog.show();
        });

        return vHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvName.setText(mData.get(position).getName());
        holder.tvNumber.setText(mData.get(position).getNumber());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout rowStudent;
        private TextView tvName;
        private TextView tvNumber;
        public MyViewHolder(View itemView) {
            super(itemView);
            rowStudent = itemView.findViewById(R.id.student_row_id);
            tvName = itemView.findViewById(R.id.student_name);
            tvNumber = itemView.findViewById(R.id.student_phone);
        }
    }
}
