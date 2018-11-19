package com.proj.changelang.models;

import android.os.Parcel;
import android.os.Parcelable;

public class FilterModel implements Parcelable {
    private String cityId;
    private String regId;
    private String price1;
    private String price2;
    private boolean withPhoto;
    private boolean barter;

    public FilterModel(){
        this.bargain = false;
        this.barter = false;
        this.withPhoto = false;
    }
    public FilterModel(String cityId, String regId, String price1, String price2, boolean withPhoto, boolean barter, boolean bargain) {
        this.cityId = cityId;
        this.regId = regId;
        this.price1 = price1;
        this.price2 = price2;
        this.withPhoto = withPhoto;
        this.barter = barter;
        this.bargain = bargain;
    }

    protected FilterModel(Parcel in) {
        cityId = in.readString();
        regId = in.readString();
        price1 = in.readString();
        price2 = in.readString();
        withPhoto = in.readByte() != 0;
        barter = in.readByte() != 0;
        bargain = in.readByte() != 0;
    }

    public static final Creator<FilterModel> CREATOR = new Creator<FilterModel>() {
        @Override
        public FilterModel createFromParcel(Parcel in) {
            return new FilterModel(in);
        }

        @Override
        public FilterModel[] newArray(int size) {
            return new FilterModel[size];
        }
    };

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getPrice1() {
        return price1;
    }

    public void setPrice1(String price1) {
        this.price1 = price1;
    }

    public String getPrice2() {
        return price2;
    }

    public void setPrice2(String price2) {
        this.price2 = price2;
    }

    public boolean isWithPhoto() {
        return withPhoto;
    }

    public void setWithPhoto(boolean withPhoto) {
        this.withPhoto = withPhoto;
    }

    public boolean isBarter() {
        return barter;
    }

    public void setBarter(boolean barter) {
        this.barter = barter;
    }

    public boolean isBargain() {
        return bargain;
    }

    public void setBargain(boolean bargain) {
        this.bargain = bargain;
    }

    private boolean bargain;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cityId);
        dest.writeString(regId);
        dest.writeString(price1);
        dest.writeString(price2);
        dest.writeByte((byte) (withPhoto ? 1 : 0));
        dest.writeByte((byte) (barter ? 1 : 0));
        dest.writeByte((byte) (bargain ? 1 : 0));
    }
}
