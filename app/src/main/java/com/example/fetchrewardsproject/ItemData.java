package com.example.fetchrewardsproject;

public class ItemData {
    public String listId;
    public String id;
    public String name;

    public ItemData(String listId, String id, String name) {
        this.listId = listId;
        this.id = id;
        this.name = name;
    }

    public String getListId() {
        return listId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
