package kz.maltabu.app.maltabukz.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Region implements Parcelable {
    private String value;
    private String name;

    private String id;
    private String firstCase;
    public ArrayList<City> cities;

    public Region(String name) {
        this.name = name;
    }
    public Region(String id, String value, String name, String firstCase) {
        this.id = id;
        this.value = value;
        this.name = name;
        this.firstCase = firstCase;
        this.cities = new ArrayList<>();
    }

    public Region() {}

    protected Region(Parcel in) {
        id = in.readString();
        value = in.readString();
        name = in.readString();
        firstCase = in.readString();
        cities = new ArrayList<City>();
        in.readTypedList(cities, City.CREATOR);
    }

    public static final Creator<Region> CREATOR = new Creator<Region>() {
        @Override
        public Region createFromParcel(Parcel in) {
            return new Region(in);
        }

        @Override
        public Region[] newArray(int size) {
            return new Region[size];
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
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(value);
        dest.writeString(name);
        dest.writeString(firstCase);
        dest.writeTypedList(cities);
    }
}
