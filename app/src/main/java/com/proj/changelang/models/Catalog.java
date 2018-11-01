package com.proj.changelang.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Catalog implements Parcelable {

    private String value;
    private String name;
    private String firstCase;
    private int count;

    public Catalog(String value, String name, String firstCase, int count) {
        this.value = value;
        this.name = name;
        this.firstCase = firstCase;
        this.count = count;
    }

    protected Catalog(Parcel in) {
        value = in.readString();
        name = in.readString();
        firstCase = in.readString();
        count = in.readInt();
    }

    public static final Creator<Catalog> CREATOR = new Creator<Catalog>() {
        @Override
        public Catalog createFromParcel(Parcel in) {
            return new Catalog(in);
        }

        @Override
        public Catalog[] newArray(int size) {
            return new Catalog[size];
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
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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
    }
}