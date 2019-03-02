package kz.maltabu.app.maltabukz.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Comment implements Parcelable {
    private String content;
    private String createdAt;
    private String name;
    private String email;

    public Comment() {}

    public Comment(String content, String createdAt, String name, String email) {
        this.content = content;
        this.createdAt = createdAt;
        this.name = name;
        this.email = email;
    }

    protected Comment(Parcel in) {
        content = in.readString();
        createdAt = in.readString();
        name = in.readString();
        email = in.readString();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
        dest.writeString(createdAt);
        dest.writeString(name);
        dest.writeString(email);
    }
}
