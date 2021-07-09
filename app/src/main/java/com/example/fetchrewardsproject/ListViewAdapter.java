package com.example.fetchrewardsproject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {

    private ArrayList<ItemData> itemDataList;
    private  ArrayList<String>distinct_listIds;
    private HashMap<String,ArrayList<ItemData>> groupedItemsDirectory;
    private SublistViewAdapter sublistViewAdapter;
    Activity activity;

    ListViewAdapter( Activity activity, ArrayList<ItemData> itemDataList){
        this.activity=activity;
        this.itemDataList = itemDataList;
        distinct_listIds = new ArrayList<>();
        groupedItemsDirectory = new HashMap<>();
        processAndFilterList();
        sortList();
    }

    static private Comparator<ItemData> sortByName = new Comparator<ItemData>() {
        @Override
        public int compare(ItemData o1, ItemData o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ListViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                sublistViewAdapter =  new SublistViewAdapter(groupedItemsDirectory.get(distinct_listIds.get(position)));
                holder.listId_items.setLayoutManager(new LinearLayoutManager(activity));
                holder.listId_items.setAdapter(sublistViewAdapter);
            }
        });
        myThread.start();

        holder.listId.setText("List Id #"+ distinct_listIds.get(position));
    }

    @Override
    public int getItemCount() {
        return groupedItemsDirectory.size();
    }

    private void sortList(){
        ArrayList<String> sortedKeys = new ArrayList<>(groupedItemsDirectory.keySet());
        Collections.sort(sortedKeys);

        for(ArrayList<ItemData> memberList: groupedItemsDirectory.values()){
            Collections.sort(memberList,sortByName);
        }

    }

    private void processAndFilterList(){

        for(ItemData item: itemDataList){
            if(!distinct_listIds.contains(item.getListId())){
                distinct_listIds.add(item.getListId());
            }
        }
        Collections.sort(distinct_listIds);

        for(ItemData item: itemDataList){
            if(!groupedItemsDirectory.containsKey(item.getListId()) && !item.getName().equals("null") && !item.getName().equals("")){
                ArrayList<ItemData> memberItems = new ArrayList<>();
                memberItems.add(item);
                groupedItemsDirectory.put(item.getListId(),memberItems);
            }
            else{
                if(!item.getName().equals("null") && !item.getName().equals("")) {
                    groupedItemsDirectory.get(item.getListId()).add(item);
                }
            }
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView listId;
        protected RecyclerView listId_items;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.listId = (TextView) itemView.findViewById(R.id.listId);
            this.listId_items = (RecyclerView) itemView.findViewById(R.id.listId_items);
        }
    }


}


