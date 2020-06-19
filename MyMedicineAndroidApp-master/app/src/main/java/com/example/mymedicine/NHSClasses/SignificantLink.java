
package com.example.mymedicine.NHSClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignificantLink {

    @SerializedName("linkRelationship")
    @Expose
    private String linkRelationship;
    @SerializedName("@type")
    @Expose
    private String type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mainEntityOfPage")
    @Expose
    private MainEntityOfPage mainEntityOfPage;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("description")
    @Expose
    private String description;

    public String getLinkRelationship() {
        return linkRelationship;
    }

    public void setLinkRelationship(String linkRelationship) {
        this.linkRelationship = linkRelationship;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MainEntityOfPage getMainEntityOfPage() {
        return mainEntityOfPage;
    }

    public void setMainEntityOfPage(MainEntityOfPage mainEntityOfPage) {
        this.mainEntityOfPage = mainEntityOfPage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
