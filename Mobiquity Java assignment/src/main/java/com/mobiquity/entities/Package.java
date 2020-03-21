package com.mobiquity.entities;

import java.util.List;

public class Package {
   private int weight;
   private List<Item> itemList;

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Package{");
        sb.append("weight=").append(weight);
        sb.append(", itemList=").append(itemList);
        sb.append('}');
        return sb.toString();
    }
}
