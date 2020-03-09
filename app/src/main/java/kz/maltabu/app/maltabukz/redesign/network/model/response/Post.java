package kz.maltabu.app.maltabukz.redesign.network.model.response;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("img")
    @Expose
    private Img img;
    @SerializedName("price")
    @Expose
    private Price price;
    @SerializedName("stat")
    @Expose
    private Stat stat;
    @SerializedName("hasImages")
    @Expose
    private Boolean hasImages;
    @SerializedName("comments")
    @Expose
    private List<Object> comments = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("hasContent")
    @Expose
    private String hasContent;
    @SerializedName("cityID")
    @Expose
    private CityID cityID;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("number")
    @Expose
    private Integer number;

    public boolean isPromoted;

    public Img getImg() {
        return img;
    }

    public void setImg(Img img) {
        this.img = img;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Stat getStat() {
        return stat;
    }

    public void setStat(Stat stat) {
        this.stat = stat;
    }

    public Boolean getHasImages() {
        return hasImages;
    }

    public void setHasImages(Boolean hasImages) {
        this.hasImages = hasImages;
    }

    public List<Object> getComments() {
        return comments;
    }

    public void setComments(List<Object> comments) {
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHasContent() {
        return hasContent;
    }

    public void setHasContent(String hasContent) {
        this.hasContent = hasContent;
    }

    public CityID getCityID() {
        return cityID;
    }

    public void setCityID(CityID cityID) {
        this.cityID = cityID;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

}