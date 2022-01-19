package com.symbol.shoppinglistv2.Other;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.symbol.shoppinglistv2.Components.MyLog;
import com.symbol.shoppinglistv2.R;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterLogs extends RecyclerView.Adapter<AdapterLogs.ViewHolder>{
    private final String TAG = "com.symbol.shoppinglistv2.Other.AdapterLogs";
    private ArrayList<MyLog> myLogs;

    public AdapterLogs(ArrayList<MyLog> myLogs) {
        this.myLogs = myLogs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_notification_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyLog log = myLogs.get(position);
        holder.tvLogListName.setText(log.getListName());
        holder.tvLogProductName.setText(log.getProductName());
        Date myDate = new Date(Date.parse(log.getExpirationDays()));
        int day = myDate.getDate();
        int month = myDate.getMonth() +1;
        int year = myDate.getYear()+1900;


        String create = day + "/" + month+ "/" + year;
        holder.tvLogExpirationDays.setText(create);

    }

    @Override
    public int getItemCount() {
        return myLogs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvLogProductName, tvLogExpirationDays, tvLogListName;
        private ImageButton ibtnLogRemove;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLogProductName = itemView.findViewById(R.id.tvLogProductName);
            tvLogExpirationDays = itemView.findViewById(R.id.tvLogExpirationDays);
            tvLogListName = itemView.findViewById(R.id.tvLogListName);
            ibtnLogRemove = itemView.findViewById(R.id.ibtnLogRemove);
            removeListener();
        }

        private void removeListener(){
            ibtnLogRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseUtil.removeLog(myLogs.get(getAdapterPosition()));
                }
            });
        }
    }

}
