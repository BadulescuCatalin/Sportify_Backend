package com.example.mongodb.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
@Data
@Document(collection = "rezervariTeren")
public class RezervareTeren {

    @Id
    private String id;
    private String idTeren;
    private HashMap<String, HashMap<Integer, Boolean>> intervale;

    public RezervareTeren(String idTeren) {
        this.idTeren = idTeren;
        this.intervale = new HashMap<>();
    }

    public String getIdTeren() {
        return idTeren;
    }

    public HashMap<String, HashMap<Integer, Boolean>> getIntervale() {
        return intervale;
    }

    public void setIdTeren(String idTeren) {
        this.idTeren = idTeren;
    }

    public void setIntervale(HashMap<String, HashMap<Integer, Boolean>> intervale) {
        this.intervale = intervale;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
