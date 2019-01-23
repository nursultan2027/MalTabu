package kz.maltabu.app.maltabukz.helpers;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PostBodyHelper {
    public ArrayList<File> files;
    public String HasImages;
    public String title;
    public String content;
    public String email;
    public String catalogID;
    public String RegionID;
    public String CityID;
    public String exchange;
    public String value;
    public String price;
    public String address;
    public ArrayList<String> phones;

    public PostBodyHelper(){}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<File> files) {
        this.files = files;
    }

    public String getHasImages() {
        return HasImages;
    }

    public void setHasImages(String hasImages) {
        HasImages = hasImages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCatalogID() {
        return catalogID;
    }

    public void setCatalogID(String catalogID) {
        this.catalogID = catalogID;
    }

    public String getRegionID() {
        return RegionID;
    }

    public void setRegionID(String regionID) {
        RegionID = regionID;
    }

    public String getCityID() {
        return CityID;
    }

    public void setCityID(String cityID) {
        CityID = cityID;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ArrayList<String> getPhones() {
        return phones;
    }

    public void setPhones(ArrayList<String> phones) {
        this.phones = phones;
    }


    public RequestBody getBody(){
        RequestBody body = null;
        if(files.size()==0&&phones.size()==1) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==0&&phones.size()==2) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==0&&phones.size()==3) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("phones[2]", phones.get(2))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==0&&phones.size()==4) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("phones[2]", phones.get(2))
                    .addFormDataPart("phones[3]", phones.get(3))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==0&&phones.size()==5) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("phones[2]", phones.get(2))
                    .addFormDataPart("phones[3]", phones.get(3))
                    .addFormDataPart("phones[4]", phones.get(4))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==1&&phones.size()==1) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==2&&phones.size()==1) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==3&&phones.size()==1) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("images[2]", files.get(2).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(2)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==4&&phones.size()==1) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("images[2]", files.get(2).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(2)))
                    .addFormDataPart("images[3]", files.get(3).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(3)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==5&&phones.size()==1) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("images[2]", files.get(2).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(2)))
                    .addFormDataPart("images[3]", files.get(3).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(3)))
                    .addFormDataPart("images[4]", files.get(4).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(4)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==6&&phones.size()==1) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("images[2]", files.get(2).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(2)))
                    .addFormDataPart("images[3]", files.get(3).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(3)))
                    .addFormDataPart("images[4]", files.get(4).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(4)))
                    .addFormDataPart("images[5]", files.get(5).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(5)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==7&&phones.size()==1) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("images[2]", files.get(2).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(2)))
                    .addFormDataPart("images[3]", files.get(3).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(3)))
                    .addFormDataPart("images[4]", files.get(4).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(4)))
                    .addFormDataPart("images[5]", files.get(5).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(5)))
                    .addFormDataPart("images[6]", files.get(6).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(6)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==8&&phones.size()==1) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("images[2]", files.get(2).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(2)))
                    .addFormDataPart("images[3]", files.get(3).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(3)))
                    .addFormDataPart("images[4]", files.get(4).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(4)))
                    .addFormDataPart("images[5]", files.get(5).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(5)))
                    .addFormDataPart("images[6]", files.get(6).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(6)))
                    .addFormDataPart("images[7]", files.get(7).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(7)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }

        if(files.size()==1&&phones.size()==2) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==2&&phones.size()==2) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==3&&phones.size()==2) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("images[2]", files.get(2).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(2)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==4&&phones.size()==2) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("images[2]", files.get(2).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(2)))
                    .addFormDataPart("images[3]", files.get(3).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(3)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==5&&phones.size()==2) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("images[2]", files.get(2).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(2)))
                    .addFormDataPart("images[3]", files.get(3).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(3)))
                    .addFormDataPart("images[4]", files.get(4).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(4)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==6&&phones.size()==2) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("images[2]", files.get(2).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(2)))
                    .addFormDataPart("images[3]", files.get(3).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(3)))
                    .addFormDataPart("images[4]", files.get(4).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(4)))
                    .addFormDataPart("images[5]", files.get(5).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(5)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==7&&phones.size()==2) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("images[2]", files.get(2).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(2)))
                    .addFormDataPart("images[3]", files.get(3).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(3)))
                    .addFormDataPart("images[4]", files.get(4).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(4)))
                    .addFormDataPart("images[5]", files.get(5).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(5)))
                    .addFormDataPart("images[6]", files.get(6).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(6)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==8&&phones.size()==2) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("images[2]", files.get(2).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(2)))
                    .addFormDataPart("images[3]", files.get(3).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(3)))
                    .addFormDataPart("images[4]", files.get(4).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(4)))
                    .addFormDataPart("images[5]", files.get(5).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(5)))
                    .addFormDataPart("images[6]", files.get(6).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(6)))
                    .addFormDataPart("images[7]", files.get(7).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(7)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }

        if(files.size()==1&&phones.size()==3) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("phones[2]", phones.get(2))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==2&&phones.size()==3) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("phones[2]", phones.get(2))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==3&&phones.size()==3) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("images[2]", files.get(2).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(2)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("phones[2]", phones.get(2))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==4&&phones.size()==3) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("images[2]", files.get(2).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(2)))
                    .addFormDataPart("images[3]", files.get(3).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(3)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("phones[2]", phones.get(2))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==5&&phones.size()==3) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("images[2]", files.get(2).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(2)))
                    .addFormDataPart("images[3]", files.get(3).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(3)))
                    .addFormDataPart("images[4]", files.get(4).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(4)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("phones[2]", phones.get(2))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==6&&phones.size()==3) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("images[2]", files.get(2).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(2)))
                    .addFormDataPart("images[3]", files.get(3).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(3)))
                    .addFormDataPart("images[4]", files.get(4).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(4)))
                    .addFormDataPart("images[5]", files.get(5).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(5)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("phones[2]", phones.get(2))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==7&&phones.size()==3) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("images[2]", files.get(2).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(2)))
                    .addFormDataPart("images[3]", files.get(3).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(3)))
                    .addFormDataPart("images[4]", files.get(4).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(4)))
                    .addFormDataPart("images[5]", files.get(5).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(5)))
                    .addFormDataPart("images[6]", files.get(6).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(6)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("phones[2]", phones.get(2))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==8&&phones.size()==3) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("images[2]", files.get(2).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(2)))
                    .addFormDataPart("images[3]", files.get(3).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(3)))
                    .addFormDataPart("images[4]", files.get(4).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(4)))
                    .addFormDataPart("images[5]", files.get(5).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(5)))
                    .addFormDataPart("images[6]", files.get(6).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(6)))
                    .addFormDataPart("images[7]", files.get(7).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(7)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("phones[2]", phones.get(2))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==1&&phones.size()==4) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("phones[2]", phones.get(2))
                    .addFormDataPart("phones[3]", phones.get(3))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==2&&phones.size()==4) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("phones[2]", phones.get(2))
                    .addFormDataPart("phones[3]", phones.get(3))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==3&&phones.size()==4) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("images[2]", files.get(2).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(2)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("phones[2]", phones.get(2))
                    .addFormDataPart("phones[3]", phones.get(3))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==4&&phones.size()==4) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("images[2]", files.get(2).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(2)))
                    .addFormDataPart("images[3]", files.get(3).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(3)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("phones[2]", phones.get(2))
                    .addFormDataPart("phones[3]", phones.get(3))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==5&&phones.size()==4) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("images[2]", files.get(2).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(2)))
                    .addFormDataPart("images[3]", files.get(3).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(3)))
                    .addFormDataPart("images[4]", files.get(4).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(4)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("phones[2]", phones.get(2))
                    .addFormDataPart("phones[3]", phones.get(3))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==6&&phones.size()==4) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("images[2]", files.get(2).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(2)))
                    .addFormDataPart("images[3]", files.get(3).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(3)))
                    .addFormDataPart("images[4]", files.get(4).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(4)))
                    .addFormDataPart("images[5]", files.get(5).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(5)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("phones[2]", phones.get(2))
                    .addFormDataPart("phones[3]", phones.get(3))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==7&&phones.size()==4) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("images[2]", files.get(2).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(2)))
                    .addFormDataPart("images[3]", files.get(3).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(3)))
                    .addFormDataPart("images[4]", files.get(4).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(4)))
                    .addFormDataPart("images[5]", files.get(5).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(5)))
                    .addFormDataPart("images[6]", files.get(6).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(6)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("phones[2]", phones.get(2))
                    .addFormDataPart("phones[3]", phones.get(3))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==8&&phones.size()==4) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("images[2]", files.get(2).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(2)))
                    .addFormDataPart("images[3]", files.get(3).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(3)))
                    .addFormDataPart("images[4]", files.get(4).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(4)))
                    .addFormDataPart("images[5]", files.get(5).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(5)))
                    .addFormDataPart("images[6]", files.get(6).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(6)))
                    .addFormDataPart("images[7]", files.get(7).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(7)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("phones[2]", phones.get(2))
                    .addFormDataPart("phones[3]", phones.get(3))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }

        if(files.size()==1&&phones.size()==5) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("phones[2]", phones.get(2))
                    .addFormDataPart("phones[3]", phones.get(3))
                    .addFormDataPart("phones[4]", phones.get(4))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==2&&phones.size()==5) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("phones[2]", phones.get(2))
                    .addFormDataPart("phones[3]", phones.get(3))
                    .addFormDataPart("phones[4]", phones.get(4))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==3&&phones.size()==5) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("images[2]", files.get(2).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(2)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("phones[2]", phones.get(2))
                    .addFormDataPart("phones[3]", phones.get(3))
                    .addFormDataPart("phones[4]", phones.get(4))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==4&&phones.size()==5) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("images[2]", files.get(2).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(2)))
                    .addFormDataPart("images[3]", files.get(3).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(3)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("phones[2]", phones.get(2))
                    .addFormDataPart("phones[3]", phones.get(3))
                    .addFormDataPart("phones[4]", phones.get(4))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==5&&phones.size()==5) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("images[2]", files.get(2).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(2)))
                    .addFormDataPart("images[3]", files.get(3).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(3)))
                    .addFormDataPart("images[4]", files.get(4).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(4)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("phones[2]", phones.get(2))
                    .addFormDataPart("phones[3]", phones.get(3))
                    .addFormDataPart("phones[4]", phones.get(4))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==6&&phones.size()==5) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("images[2]", files.get(2).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(2)))
                    .addFormDataPart("images[3]", files.get(3).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(3)))
                    .addFormDataPart("images[4]", files.get(4).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(4)))
                    .addFormDataPart("images[5]", files.get(5).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(5)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("phones[2]", phones.get(2))
                    .addFormDataPart("phones[3]", phones.get(3))
                    .addFormDataPart("phones[4]", phones.get(4))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==7&&phones.size()==5) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("images[2]", files.get(2).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(2)))
                    .addFormDataPart("images[3]", files.get(3).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(3)))
                    .addFormDataPart("images[4]", files.get(4).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(4)))
                    .addFormDataPart("images[5]", files.get(5).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(5)))
                    .addFormDataPart("images[6]", files.get(6).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(6)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("phones[2]", phones.get(2))
                    .addFormDataPart("phones[3]", phones.get(3))
                    .addFormDataPart("phones[4]", phones.get(4))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }
        if(files.size()==8&&phones.size()==5) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("images[0]", files.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(0)))
                    .addFormDataPart("images[1]", files.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(1)))
                    .addFormDataPart("images[2]", files.get(2).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(2)))
                    .addFormDataPart("images[3]", files.get(3).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(3)))
                    .addFormDataPart("images[4]", files.get(4).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(4)))
                    .addFormDataPart("images[5]", files.get(5).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(5)))
                    .addFormDataPart("images[6]", files.get(6).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(6)))
                    .addFormDataPart("images[7]", files.get(7).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(7)))
                    .addFormDataPart("title", title)
                    .addFormDataPart("exchange", exchange)
                    .addFormDataPart("phones[0]", phones.get(0))
                    .addFormDataPart("phones[1]", phones.get(1))
                    .addFormDataPart("phones[2]", phones.get(2))
                    .addFormDataPart("phones[3]", phones.get(3))
                    .addFormDataPart("phones[4]", phones.get(4))
                    .addFormDataPart("mail", email)
                    .addFormDataPart("priceKind", price)
                    .addFormDataPart("priceValue", value)
                    .addFormDataPart("regionID", RegionID)
                    .addFormDataPart("cityID", CityID)
                    .addFormDataPart("hasImages",HasImages)
                    .addFormDataPart("catalogID", catalogID)
                    .addFormDataPart("content", content)
                    .addFormDataPart("address", address)
                    .build();
        }

        return body;
    }
}
