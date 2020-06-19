package com.example.mymedicine.NHSClasses;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Treatment {

    @SerializedName("mainEntityOfPage")
    @Expose
    private List<MainEntityOfPage> mainEntityOfPage = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;


    public List<MainEntityOfPage> getMainEntityOfPage() {
        return mainEntityOfPage;
    }

    public void setMainEntityOfPage(List<MainEntityOfPage> mainEntityOfPage) {
        this.mainEntityOfPage = mainEntityOfPage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}