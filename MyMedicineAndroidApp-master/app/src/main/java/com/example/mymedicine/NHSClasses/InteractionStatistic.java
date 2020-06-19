
package com.example.mymedicine.NHSClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InteractionStatistic {

    @SerializedName("interactionService")
    @Expose
    private InteractionService interactionService;
    @SerializedName("@type")
    @Expose
    private String type;

    public InteractionService getInteractionService() {
        return interactionService;
    }

    public void setInteractionService(InteractionService interactionService) {
        this.interactionService = interactionService;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
