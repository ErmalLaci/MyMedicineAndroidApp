
package com.example.mymedicine.NHSClasses;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Breadcrumb {

    @SerializedName("@type")
    @Expose
    private String type;
    @SerializedName("@context")
    @Expose
    private String context;
    @SerializedName("itemListElement")
    @Expose
    private List<ItemListElement> itemListElement = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public List<ItemListElement> getItemListElement() {
        return itemListElement;
    }

    public void setItemListElement(List<ItemListElement> itemListElement) {
        this.itemListElement = itemListElement;
    }

}
