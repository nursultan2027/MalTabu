package com.proj.changelang.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Post implements Parcelable{
    private String createdAt;
    private String phones;
    private String updatedAt;
    private String title;
    private String visitors;
    private String content;
    private String catalogID;
    private String categoryID;
    private String regionID;
    private String cityID;
    private String price;
    private String priceValue;
    private String address;
    private String number;
    private ArrayList<Image> images;
    private boolean exchange;

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getVisitors() {
        return visitors;
    }

    public void setVisitors(String visitors) {
        this.visitors = visitors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCatalogID() {
        return catalogID;
    }

    public void setCatalogID(String catalogID) {
        this.catalogID = catalogID;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(String priceValue) {
        this.priceValue = priceValue;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    public Post(String cityID, String price, String number, ArrayList<Image> images){
        this.cityID = cityID;
        this.price = price;
        this.number = number;
        this.images = images;
    }
    public Post(int visitors, String createdAt, String title, String content, String cityID, String price, String number, ArrayList<Image> images) {
        this.visitors = String.valueOf(visitors);
        this.createdAt = createdAt;
        this.title = title;
        this.content = content;
        this.cityID = cityID;
        this.price = price;
        this.number = number;
        this.images = images;
    }


    public Post(int visitors, String createdAt, String title, String cityID, String price, String number, ArrayList<Image> images) {
        this.createdAt = createdAt;
        this.title = title;
        this.visitors = String.valueOf(visitors);
        this.cityID = cityID;
        this.price = price;
        this.number = number;
        this.images = images;
    }


    protected Post(Parcel in) {
        phones = in.readString();
        visitors = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        title = in.readString();
        content = in.readString();
        catalogID = in.readString();
        categoryID = in.readString();
        regionID = in.readString();
        cityID = in.readString();
        price = in.readString();
        priceValue = in.readString();
        address = in.readString();
        number = in.readString();
        images = new ArrayList<Image>();
        in.readTypedList(images, Image.CREATOR);
        exchange = in.readByte() != 0;
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(phones);
        dest.writeString(visitors);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(catalogID);
        dest.writeString(categoryID);
        dest.writeString(regionID);
        dest.writeString(cityID);
        dest.writeString(price);
        dest.writeString(priceValue);
        dest.writeString(address);
        dest.writeString(number);
        dest.writeTypedList(images);
        dest.writeByte((byte) (exchange ? 1 : 0));
    }
}
