package kz.maltabu.app.maltabukz.redesign.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPostsRequest {

    @SerializedName("onlyExchange")
    @Expose
    private Boolean onlyExchange;
    @SerializedName("onlyEmergency")
    @Expose
    private Boolean onlyEmergency;
    @SerializedName("onlyImages")
    @Expose
    private Boolean onlyImages;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("byTime")
    @Expose
    private Boolean byTime;
    @SerializedName("increment")
    @Expose
    private Boolean increment;
    @SerializedName("categoryID")
    @Expose
    private String categoryID;
    @SerializedName("catalogID")
    @Expose
    private String catalogID;
    @SerializedName("regionID")
    @Expose
    private String regionID;
    @SerializedName("cityID")
    @Expose
    private String cityID;
    @SerializedName("fromPrice")
    @Expose
    private int fromPrice;
    @SerializedName("toPrice")
    @Expose
    private int toPrice;
    @SerializedName("countPosts")
    @Expose
    private Boolean countPosts;

    public GetPostsRequest(Integer page, Boolean byTime, Boolean increment, Boolean countPosts) {
        this.page = page;
        this.byTime = byTime;
        this.increment = increment;
        this.countPosts = countPosts;
    }

    public Boolean getOnlyExchange() {
        return onlyExchange;
    }

    public void setOnlyExchange(Boolean onlyExchange) {
        this.onlyExchange = onlyExchange;
    }

    public Boolean getOnlyEmergency() {
        return onlyEmergency;
    }

    public void setOnlyEmergency(Boolean onlyEmergency) {
        this.onlyEmergency = onlyEmergency;
    }

    public Boolean getOnlyImages() {
        return onlyImages;
    }

    public void setOnlyImages(Boolean onlyImages) {
        this.onlyImages = onlyImages;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Boolean getByTime() {
        return byTime;
    }

    public void setByTime(Boolean byTime) {
        this.byTime = byTime;
    }

    public Boolean getIncrement() {
        return increment;
    }

    public void setIncrement(Boolean increment) {
        this.increment = increment;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public Boolean getCountPosts() {
        return countPosts;
    }

    public void setCountPosts(Boolean countPosts) {
        this.countPosts = countPosts;
    }

    public String getCatalogID() {
        return catalogID;
    }

    public void setCatalogID(String catalogID) {
        this.catalogID = catalogID;
    }

    public String getRegionID() {
        return regionID;
    }

    public void setRegionID(String regionID) {
        this.regionID = regionID;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public int getFromPrice() {
        return fromPrice;
    }

    public void setFromPrice(int fromPrice) {
        this.fromPrice = fromPrice;
    }

    public int getToPrice() {
        return toPrice;
    }

    public void setToPrice(int toPrice) {
        this.toPrice = toPrice;
    }
}
