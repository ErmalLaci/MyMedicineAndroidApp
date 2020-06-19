
package com.example.mymedicine.NHSClasses;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Condition {

    @SerializedName("genre")
    @Expose
    private String genre;
    @SerializedName("@context")
    @Expose
    private String context;
    @SerializedName("mainEntityOfPage")
    @Expose
    private List<MainEntityOfPage> mainEntityOfPage = null;
    @SerializedName("breadcrumb")
    @Expose
    private Breadcrumb breadcrumb;
    @SerializedName("dateModified")
    @Expose
    private String dateModified;
    @SerializedName("author")
    @Expose
    private Author author;
    @SerializedName("@type")
    @Expose
    private String type;
    @SerializedName("copyrightHolder")
    @Expose
    private CopyrightHolder copyrightHolder;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("license")
    @Expose
    private String license;
    @SerializedName("relatedLink")
    @Expose
    private List<RelatedLink> relatedLink = null;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("interactionStatistic")
    @Expose
    private List<InteractionStatistic> interactionStatistic = null;
    @SerializedName("lastReviewed")
    @Expose
    private List<String> lastReviewed = null;
    @SerializedName("alternativeHeadline")
    @Expose
    private String alternativeHeadline;

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public List<MainEntityOfPage> getMainEntityOfPage() {
        return mainEntityOfPage;
    }

    public void setMainEntityOfPage(List<MainEntityOfPage> mainEntityOfPage) {
        this.mainEntityOfPage = mainEntityOfPage;
    }

    public Breadcrumb getBreadcrumb() {
        return breadcrumb;
    }

    public void setBreadcrumb(Breadcrumb breadcrumb) {
        this.breadcrumb = breadcrumb;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

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

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public List<RelatedLink> getRelatedLink() {
        return relatedLink;
    }

    public void setRelatedLink(List<RelatedLink> relatedLink) {
        this.relatedLink = relatedLink;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<InteractionStatistic> getInteractionStatistic() {
        return interactionStatistic;
    }

    public void setInteractionStatistic(List<InteractionStatistic> interactionStatistic) {
        this.interactionStatistic = interactionStatistic;
    }

    public List<String> getLastReviewed() {
        return lastReviewed;
    }

    public void setLastReviewed(List<String> lastReviewed) {
        this.lastReviewed = lastReviewed;
    }

    public String getAlternativeHeadline() {
        return alternativeHeadline;
    }

    public void setAlternativeHeadline(String alternativeHeadline) {
        this.alternativeHeadline = alternativeHeadline;
    }

}