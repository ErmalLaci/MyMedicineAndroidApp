
package com.example.mymedicine.NHSClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemListElement {

    @SerializedName("@type")
    @Expose
    private String type;
    @SerializedName("item")
    @Expose
    private Item item;
    @SerializedName("position")
    @Expose
    private Integer position;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

}
