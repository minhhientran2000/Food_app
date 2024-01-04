package com.example.test;

import java.io.Serializable;

public class FoodDomain implements Serializable {

    private String id;
    private String title;
    private  String pic;
    private  String description;
    private String quantity;
    private  Double fee;
    private  int numberInCart;

    private String timestamp;
    private String category;
    private String categoryId;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public FoodDomain() {
    }

    public FoodDomain(String id, String title, String pic, String description, String quantity, Double fee, String timestamp, String category, String categoryId) {
        this.id = id;
        this.title = title;
        this.pic = pic;
        this.description = description;
        this.quantity = quantity;
        this.fee = fee;

        this.timestamp = timestamp;
        this.category = category;
        this.categoryId = categoryId;
    }



    public FoodDomain(String title, String pic, String description, Double  fee, int numberInCart) {
        this.title = title;
        this.pic = pic;
        this.description = description;
        this.fee = fee;
        this.numberInCart = numberInCart;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double  fee) {
        this.fee = fee;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
