package kz.maltabu.app.maltabukz.redesign.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class News implements Parcelable {

    @SerializedName("stat")
    @Expose
    private Stat stat;
    @SerializedName("pictures")
    @Expose
    private Picture pictures;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("number")
    @Expose
    private Integer number;

    protected News(Parcel in) {
        stat = in.readParcelable(Stat.class.getClassLoader());
        pictures = in.readParcelable(Picture.class.getClassLoader());
        id = in.readString();
        title = in.readString();
        desc = in.readString();
        lang = in.readString();
        createdAt = in.readString();
        if (in.readByte() == 0) {
            number = null;
        } else {
            number = in.readInt();
        }
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    public Stat getStat() {
        return stat;
    }

    public void setStat(Stat stat) {
        this.stat = stat;
    }

    public Picture getPictures() {
        return pictures;
    }

    public void setPictures(Picture pictures) {
        this.pictures = pictures;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(stat, flags);
        dest.writeParcelable(pictures, flags);
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(desc);
        dest.writeString(lang);
        dest.writeString(createdAt);
        if (number == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(number);
        }
    }
}