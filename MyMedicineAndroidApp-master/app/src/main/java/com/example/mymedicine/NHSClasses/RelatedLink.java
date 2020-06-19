
package com.example.mymedicine.NHSClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RelatedLink {

    @SerializedName("linkRelationship")
    @Expose
    private String linkRelationship;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("@type")
    @Expose
    private String type;
    @SerializedName("alternativeHeadline")
    @Expose
    private String alternativeHeadline;
    @SerializedName("position")
    @Expose
    private Integer position;

    public String getLinkRelationship() {
        return linkRelationship;
    }

    public void setLinkRelationship(String linkRelationship) {
        this.linkRelationship = linkRelationship;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAlternativeHeadline() {
        return alternativeHeadline;
    }

    public void setAlternativeHeadline(String alternativeHeadline) {
        this.alternativeHeadline = alternativeHeadline;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

}
