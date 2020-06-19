
package com.example.mymedicine.NHSClasses;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Conditions {

    @SerializedName("@type")
    @Expose
    private String type;
    @SerializedName("copyrightHolder")
    @Expose
    private CopyrightHolder copyrightHolder;
    @SerializedName("license")
    @Expose
    private String license;
    @SerializedName("significantLink")
    @Expose
    private List<SignificantLink> significantLink = null;
    @SerializedName("author")
    @Expose
    private Author author;
    @SerializedName("interactionStatistic")
    @Expose
    private List<InteractionStatistic> interactionStatistic = null;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("@context")
    @Expose
    private String context;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("breadcrumb")
    @Expose
    private Breadcrumb breadcrumb;
    @SerializedName("genre")
    @Expose
    private String genre;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CopyrightHolder getCopyrightHolder() {
        return copyrightHolder;
    }

    public void setCopyrightHolder(CopyrightHolder copyrightHolder) {
        this.copyrightHolder = copyrightHolder;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public List<SignificantLink> getSignificantLink() {
        return significantLink;
    }

    public void setSignificantLink(List<SignificantLink> significantLink) {
        this.significantLink = significantLink;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<InteractionStatistic> getInteractionStatistic() {
        return interactionStatistic;
    }

    public void setInteractionStatistic(List<InteractionStatistic> interactionStatistic) {
        this.interactionStatistic = interactionStatistic;
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

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Breadcrumb getBreadcrumb() {
        return breadcrumb;
    }

    public void setBreadcrumb(Breadcrumb breadcrumb) {
        this.breadcrumb = breadcrumb;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}
