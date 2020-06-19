package com.example.mymedicine.NHSClasses;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainEntityOfPage {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("identifier")
    @Expose
    private String identifier;
    @SerializedName("@type")
    @Expose
    private String type;
    @SerializedName("mainEntityOfPage")
    @Expose
    private List<MainEntityOfPage_> mainEntityOfPage = null;
    @SerializedName("keywords")
    @Expose
    private List<String> keywords = null;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<MainEntityOfPage_> getMainEntityOfPage() {
        return mainEntityOfPage;
    }

    public void setMainEntityOfPage(List<MainEntityOfPage_> mainEntityOfPage) {
        this.mainEntityOfPage = mainEntityOfPage;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

}
