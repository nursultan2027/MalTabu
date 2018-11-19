package com.proj.changelang.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable {
    private String extra_small;
    private String small;
    private String medium;
    private String big;

    public Image(String small){
        this.small =small;
    }
    public Image(String extra_small, String small, String medium, String big) {
        this.extra_small = extra_small;
        this.small = small;
        this.medium = medium;
        this.big = big;
    }

    protected Image(Parcel in) {
        extra_small = in.readString();
        small = in.readString();
        medium = in.readString();
        big = in.readString();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public String getExtra_small() {

        return extra_small;
    }

    public void setExtra_small(String extra_small) {
        this.extra_small = extra_small;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(extra_small);
        dest.writeString(small);
        dest.writeString(medium);
        dest.writeString(big);
    }
}
