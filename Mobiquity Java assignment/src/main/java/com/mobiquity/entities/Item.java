package com.mobiquity.entities;

/**
 * @author Denzil Gideon M. Daulo
 * Item Entity
 * Holds the information about the items in the package
 */
public class Item {
    private int indexNumber;
    private double weight;
    private String price;

    public int getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(int indexNumber) {
        this.indexNumber = indexNumber;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Item{");
        sb.append("indexNumber=").append(indexNumber);
        sb.append(", weight=").append(weight);
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }
}
