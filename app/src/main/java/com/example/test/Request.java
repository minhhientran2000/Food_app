package com.example.test;



import java.util.ArrayList;
import java.util.List;

public class Request {
    private String id;
    private String phone;
    private String name;

    private String address;
    private String total;
    private ArrayList<FoodDomain>  foodOrder ;
    private String Status;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Request() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Request(String id, String phone, String name, String address, String total, ArrayList<FoodDomain> foodOrder) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.total = total;
        this.foodOrder = foodOrder;
        this.Status = "0";
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ArrayList<FoodDomain> getFoodOrder() {
        return foodOrder ;
    }

    public void setFoodOrder(ArrayList<FoodDomain> foodOrder) {
        this.foodOrder = foodOrder;
    }
}

