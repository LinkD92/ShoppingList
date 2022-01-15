package com.symbol.shoppinglistv2.Other;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.symbol.shoppinglistv2.Components.SharedMember;
import com.symbol.shoppinglistv2.R;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterSharedMembers extends RecyclerView.Adapter<AdapterSharedMembers.ViewHolder> {
    private ArrayList<SharedMember> sharedMembers;
    private MutableLiveData<HashMap<String, SharedMember>> tracker;

    public AdapterSharedMembers(ArrayList<SharedMember> sharedMembers, MutableLiveData tracker) {
        this.sharedMembers = sharedMembers;
        this.tracker = tracker;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_shared_member, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SharedMember sharedMember = sharedMembers.get(position);
        holder.tvEmailAddress.setText(sharedMember.getEmail());
    }

    @Override
    public int getItemCount() {
        return sharedMembers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvEmailAddress;
        private ImageButton ibtnRemoveMember;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmailAddress = itemView.findViewById(R.id.tvItemSharedMemeberEmail);
            ibtnRemoveMember = itemView.findViewById(R.id.ibtnItemRemoveMember);
            removeListener();
        }

        private void removeListener(){
            ibtnRemoveMember.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedMember sharedMember = sharedMembers.get(getAdapterPosition());
                    sharedMembers.remove(getAdapterPosition());
                    HashMap<String, SharedMember> tempHash = tracker.getValue();
                    tempHash.remove(sharedMember.getUid());
                    tracker.setValue(tempHash);
                }
            });
        }
    }
}
