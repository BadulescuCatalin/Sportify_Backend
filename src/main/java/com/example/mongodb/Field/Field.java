package com.example.mongodb.Field;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "fields")
public class Field {
    @Id
    private String id;
    private Boolean Basketball;
    private Boolean Football;
    private Boolean Tenis;
    private Boolean Handball;
    private String location;
    private Boolean night_lighting;
    private Boolean synthetic;
    private Boolean indoor_cover;

    public Field() {}

    public Field(String location, Boolean night_lighting, Boolean synthetic, Boolean Basketball, Boolean Football, Boolean Handball, Boolean Tenis, Boolean indoor_cover) {
        this.Basketball = Basketball;
        this.Football = Football;
        this.Tenis = Tenis;
        this.Handball = Handball;
        this.location = location;
        this.night_lighting = night_lighting;
        this.synthetic = synthetic;
        this.indoor_cover = indoor_cover;
    }

    // Getters and setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Boolean getBasketball() {
        return Basketball;
    }

    public void setBasketball(Boolean basketball) {
        Basketball = basketball;
    }

    public Boolean getFootball() {
        return Football;
    }

    public void setFootball(Boolean football) {
        Football = football;
    }

    public Boolean getTenis() {
        return Tenis;
    }

    public void setTenis(Boolean tenis) {
        Tenis = tenis;
    }

    public Boolean getHandball() {
        return Handball;
    }

    public void setHandball(Boolean handball) {
        Handball = handball;
    }

    public Boolean getindoor_cover() {
        return indoor_cover;
    }

    public void setindoor_cover(Boolean indoor_cover) {
        this.indoor_cover = indoor_cover;
    }

    public void setnight_lighting(Boolean night_lighting) {
        this.night_lighting = night_lighting;
    }

    public Boolean getnight_lighting() {
        return night_lighting;
    }

    public void setsynthetic(Boolean synthetic) {
        this.synthetic = synthetic;
    }

    public Boolean getsynthetic() {
        return synthetic;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
}