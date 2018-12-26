package com.proj.changelang.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Transaction{
    private String createdAt;
    private String kind;
    private String source;
    private String value;
    private String postNumber;
    private String title;

    public Transaction(String createdAt, String kind, String source, String value) {
        this.createdAt = createdAt;
        this.value = value;
        this.kind = kind;
        this.source = source;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(String postNumber) {
        this.postNumber = postNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
