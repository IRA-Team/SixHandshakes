package com.irateam.sixhandshakes.model;

import java.util.List;

public class ResponseEntry {

    private int count;
    private List<Integer> items;
    private int id;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Integer> getItems() {
        return items;
    }

    public void setItems(List<Integer> items) {
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
