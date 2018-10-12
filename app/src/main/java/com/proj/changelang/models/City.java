package com.proj.changelang.models;

import android.os.Parcel;
import android.os.Parcelable;

public class City implements Parcelable {

    private String value;
    private String name;
    private String firstCase;

    public City(String value, String name, String firstCase) {
        this.value = value;
        this.name = name;
        this.firstCase = firstCase;
    }

    protected City(Parcel in) {
        value = in.readString();
        name = in.readString();
        firstCase = in.readString();
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

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

    public String getFirstCase() {
        return firstCase;
    }

    public void setFirstCase(String firstCase) {
        this.firstCase = firstCase;
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
    }
}
