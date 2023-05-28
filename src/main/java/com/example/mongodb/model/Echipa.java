package com.example.mongodb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@Document(collection = "echipe")
@JsonIgnoreProperties(value = { "_id" }, allowGetters = true)
public class Echipa {

    @Id
    private String id;
    private String numeEchipa;
    private String descriereEchipa;
    private int nrMembriActuali;
    private int numarMembriDoriti;
    private String emailCapitan;
    HashMap<String, Integer> emailuriParticipant;

    public Echipa(String numeEchipa, String descriereEchipa, int nrMembriActuali, int numarMembriDoriti, String emailCapitan) {
        this.numeEchipa = numeEchipa;
        this.descriereEchipa = descriereEchipa;
        this.nrMembriActuali = nrMembriActuali;
        this.numarMembriDoriti = numarMembriDoriti;
        this.emailCapitan = emailCapitan;
        this.emailuriParticipant = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public String getNumeEchipa() {
        return numeEchipa;
    }

    public String getDescriereEchipa() {
        return descriereEchipa;
    }

    public int getNrMembriActuali() {
        return nrMembriActuali;
    }

    public int getNumarMembriDoriti() {
        return numarMembriDoriti;
    }

    public String getEmailCapitan() {
        return emailCapitan;
    }

    public HashMap<String, Integer> getEmailuriParticipant() {
        return emailuriParticipant;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNumeEchipa(String numeEchipa) {
        this.numeEchipa = numeEchipa;
    }

    public void setDescriereEchipa(String descriereEchipa) {
        this.descriereEchipa = descriereEchipa;
    }

    public void setNrMembriActuali(int nrMembriActuali) {
        this.nrMembriActuali = nrMembriActuali;
    }

    public void setNumarMembriDoriti(int numarMembriDoriti) {
        this.numarMembriDoriti = numarMembriDoriti;
    }

    public void setEmailCapitan(String emailCapitan) {
        this.emailCapitan = emailCapitan;
    }

    public void setEmailuriParticipant(HashMap<String, Integer> emailuriParticipant) {
        this.emailuriParticipant = emailuriParticipant;
    }
}
