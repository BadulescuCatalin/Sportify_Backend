package com.example.mongodb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "rezervari")
@JsonIgnoreProperties(value = { "_id" }, allowGetters = true)
public class Rezervare {
    @Id
    String id;
    String idTeren;
    String emailClient;
    String emailOwner;
    String interval;
    String data;

    public Rezervare(String idTeren, String emailClient, String emailOwner, String interval, String data) {
        this.idTeren = idTeren;
        this.emailClient = emailClient;
        this.emailOwner = emailOwner;
        this.interval = interval;
        this.data = data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public String getId() {
        return id;
    }

    public String getIdTeren() {
        return idTeren;
    }

    public String getEmailClient() {
        return emailClient;
    }

    public String getEmailOwner() {
        return emailOwner;
    }

    public String getInterval() {
        return interval;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIdTeren(String idTeren) {
        this.idTeren = idTeren;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }

    public void setEmailOwner(String emailOwner) {
        this.emailOwner = emailOwner;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }
}
