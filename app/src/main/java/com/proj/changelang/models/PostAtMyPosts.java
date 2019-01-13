package com.proj.changelang.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class PostAtMyPosts{
    private String name;
    private String createdAt;
    private String status;
    private String title;
    private ArrayList<String> img;


    public PostAtMyPosts(String name, String createdAt, String status, String title) {
        this.name = name;
        this.createdAt = createdAt;
        this.status = status;
        this.title = title;
        this.img = new ArrayList<>();
    }


    public ArrayList<String> getImg() {
        return img;
    }

    public void setImg(ArrayList<String> img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
