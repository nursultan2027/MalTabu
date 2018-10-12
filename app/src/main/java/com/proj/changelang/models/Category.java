package com.proj.changelang.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Category implements Parcelable {
    private String value;
    private String name;
    private String firstCase;
    private int count;
    public ArrayList<Catalog> catalogs;

    public Category(String name) {
        this.name = name;
    }
    public Category(String value, String name, String firstCase, int count) {
        this.value = value;
        this.name = name;
        this.firstCase = firstCase;
        this.count = count;
        this.catalogs = new ArrayList<>();
    }

    public Category() {}

    protected Category(Parcel in) {
        value = in.readString();
        name = in.readString();
        firstCase = in.readString();
        count = in.readInt();
        catalogs = new ArrayList<Catalog>();
        in.readTypedList(catalogs, Catalog.CREATOR);
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public String getFirstCase() {
        return firstCase;
    }

    public void setFirstCase(String firstCase) {
        this.firstCase = firstCase;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(value);
        dest.writeString(name);
        dest.writeString(firstCase);
        dest.writeInt(count);
        dest.writeTypedList(catalogs);
    }
}
