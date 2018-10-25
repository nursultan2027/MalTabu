package com.proj.changelang.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Item implements Parcelable{

    private String name;
    private String price;
    private String location;
    private String date;
    private int [] imgCount;
    public ArrayList<String> comments;

    public Item(String name, String price, String location, String date, int[] imgCount) {
        this.name = name;
        this.price = price;
        this.location = location;
        this.date = date;
        this.imgCount = imgCount;
        this.comments = new ArrayList<>();
    }

    protected Item(Parcel in) {
        name = in.readString();
        price = in.readString();
        location = in.readString();
        date = in.readString();
        imgCount = in.createIntArray();
        comments = in.createStringArrayList();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int[] getImgCount() {
        return imgCount;
    }
    public int getImg() {
        return imgCount[0];
    }

    public void setImgCount(int[] imgCount) {
        this.imgCount = imgCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(location);
        dest.writeString(date);
        dest.writeIntArray(imgCount);
        dest.writeStringList(comments);
    }
}
