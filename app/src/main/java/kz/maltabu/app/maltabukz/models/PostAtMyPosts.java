package kz.maltabu.app.maltabukz.models;

import java.util.ArrayList;

public class PostAtMyPosts{
    private String name;
    private String createdAt;
    private String status;
    private String title;
    private String catalog;
    private String category;
    private String number;
    private String price;
    private String visitors;
    private String comments;
    private String phones;
    private String adID;
    private String catalogID;



    private ArrayList<String> img;


    public PostAtMyPosts(String adID, String catalogID, String visitors, String comments, String phones, String number, String price, String catalog, String category, String createdAt, String status, String title) {
        this.adID = adID;
        this.catalogID = catalogID;
        this.category = category;
        this.catalog = catalog;
        this.createdAt = createdAt;
        this.number = number;
        this.price = price;
        this.status = status;
        this.title = title;
        this.comments= comments;
        this.visitors = visitors;
        this.phones= phones;
        this.img = new ArrayList<>();
    }

    public PostAtMyPosts(String name, String createdAt, String status, String title) {
        this.name = name;
        this.createdAt = createdAt;
        this.status = status;
        this.title = title;
        this.img = new ArrayList<>();
    }


    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ArrayList<String> getImg() {
        return img;
    }

    public void setImg(ArrayList<String> img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVisitors() {
        return visitors;
    }

    public void setVisitors(String visitors) {
        this.visitors = visitors;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public String getAdID() {
        return adID;
    }

    public void setAdID(String adID) {
        this.adID = adID;
    }

    public String getCatalogID() {
        return catalogID;
    }

    public void setCatalogID(String catalogID) {
        this.catalogID = catalogID;
    }
}
