package kz.maltabu.app.maltabukz.redesign.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rel {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("extra_small")
    @Expose
    private String extraSmall;
    @SerializedName("small")
    @Expose
    private String small;
    @SerializedName("medium")
    @Expose
    private String medium;
    @SerializedName("big")
    @Expose
    private String big;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExtraSmall() {
        return extraSmall;
    }

    public void setExtraSmall(String extraSmall) {
        this.extraSmall = extraSmall;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getBig() {
        return big;
    }

    public void setBig(String big) {
        this.big = big;
    }

}