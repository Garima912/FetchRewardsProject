package com.example.fetchrewardsproject;

// this class is the model class for the item data fetched from the url
public class ItemData {
    private String listId;
    private String id;
    private String name;

    public ItemData(String listId, String id, String name) {
        this.listId = listId;
        this.id = id;
        this.name = name;
    }

    //getter methods for the private members
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
