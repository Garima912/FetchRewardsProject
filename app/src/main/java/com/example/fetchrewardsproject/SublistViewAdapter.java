package com.example.fetchrewardsproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class SublistViewAdapter extends RecyclerView.Adapter<SublistViewAdapter.ViewHolder> {

    private ArrayList<ItemData> itemData;

    public SublistViewAdapter( ArrayList<ItemData> itemData) {
        this.itemData = itemData;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_id_item,parent,false);
        System.out.println("Sublist Adapter Created");
        return new SublistViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.id.setText(itemData.get(position).getId());
        holder.name.setText(itemData.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return itemData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView id;
        protected  TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.id = (TextView) itemView.findViewById(R.id.id);
            this.name = (TextView) itemView.findViewById(R.id.name);
            System.out.println("Sublist View Created");
        }
    }
}
