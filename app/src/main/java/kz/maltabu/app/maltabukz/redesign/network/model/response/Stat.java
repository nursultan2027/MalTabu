package kz.maltabu.app.maltabukz.redesign.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stat implements Parcelable {

    @SerializedName("visitors")
    @Expose
    private Integer visitors;
    @SerializedName("phone")
    @Expose
    private Integer phone;

    protected Stat(Parcel in) {
        if (in.readByte() == 0) {
            visitors = null;
        } else {
            visitors = in.readInt();
        }
        if (in.readByte() == 0) {
            phone = null;
        } else {
            phone = in.readInt();
        }
    }

    public static final Creator<Stat> CREATOR = new Creator<Stat>() {
        @Override
        public Stat createFromParcel(Parcel in) {
            return new Stat(in);
        }

        @Override
        public Stat[] newArray(int size) {
            return new Stat[size];
        }
    };

    public Integer getVisitors() {
        return visitors;
    }

    public void setVisitors(Integer visitors) {
        this.visitors = visitors;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (visitors == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(visitors);
        }
        if (phone == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(phone);
        }
    }


}