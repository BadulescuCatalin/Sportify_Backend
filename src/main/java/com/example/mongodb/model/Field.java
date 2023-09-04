package com.example.mongodb.model;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "fields2")
public class Field {
    @Id
    private String id;
    private String owner;
    private String city;
    private String address;
    private String description;
    private Integer price;
    private Boolean basketball;
    private Boolean football;
    private Boolean tennis;
    private Binary fileData;


    public Field() {}

    public Field(String id, String owner, String city, String address, String description, Integer price, Boolean basketball, Boolean football, Boolean tennis) {
        this.id = id;
        this.owner = owner;
        this.city = city;
        this.address = address;
        this.description = description;
        this.price = price;
        this.basketball = basketball;
        this.football = football;
        this.tennis = tennis;
    }
    // Getters and setters

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPrice() {
        return price;
    }

    public Boolean getBasketball() {
        return basketball;
    }

    public Boolean getFootball() {
        return football;
    }

    public Boolean getTennis() {
        return tennis;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setBasketball(Boolean basketball) {
        this.basketball = basketball;
    }

    public void setFootball(Boolean football) {
        this.football = football;
    }

    public void setTennis(Boolean tennis) {
        this.tennis = tennis;
    }

    public Binary getFileData() {
        return fileData;
    }

    public void setFileData(Binary fileData) {
        this.fileData = fileData;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}