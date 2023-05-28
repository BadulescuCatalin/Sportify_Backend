package com.example.mongodb.controller;


import com.example.mongodb.model.Field;
import com.example.mongodb.model.Rezervare;
import com.example.mongodb.model.RezervareTeren;
import com.example.mongodb.repository.RezervareRepository;
import com.example.mongodb.repository.RezervareTerenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController

public class RezervareController {
    @Autowired
    RezervareRepository rezervareRepository;
    @Autowired
    RezervareTerenRepository rezervareTerenRepository;

    // iau toate rezervarile
    @GetMapping("/rezervari")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<List<Rezervare>> getAllRezervare() {
        List<Rezervare> rezervari = rezervareRepository.findAll();
        return ResponseEntity.ok().body(rezervari);
    }

    // iau toate rezervarile pentru client + de vazut token
    @PostMapping("/rezervari/client/{emailClient}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<List<Rezervare>> getAllRezervareClient(@PathVariable String emailClient) {
        List<Rezervare> rezervari = rezervareRepository.findAllByEmailClient(emailClient);
        return ResponseEntity.ok().body(rezervari);
    }

    // iau toate rezervarile pentru owner + de vazut token
    @PostMapping("/rezervari/owner/{emailOwner}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<List<Rezervare>> getAllRezervareOwner(@PathVariable String emailOwner) {
        List<Rezervare> rezervari = rezervareRepository.findAllByEmailOwner(emailOwner);
        return ResponseEntity.ok().body(rezervari);
    }

    // fac p rezervare (la fel pt client si ownwer) + de  vazut token si trimis email
    @PostMapping("/rezervari")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Rezervare> postRezervare(
                                                         @RequestParam(value = "idTeren") String idTeren,
                                                         @RequestParam(value = "emailClient") String emailClient,
                                                         @RequestParam(value = "emailOwner") String emailOwner,
                                                         @RequestParam(value = "interval") String interval,
                                                         @RequestParam(value = "data") String data
                                                         ) {
        Rezervare rezervare = new Rezervare(idTeren, emailClient, emailOwner, interval, data);
        rezervareRepository.save(rezervare);
        // plus de trimis email
        return ResponseEntity.ok().body(rezervare);

    }

    // sterg rezervare + de vazut token daca sunt owner si sterg rezervare + sa dau email
    // sau sa vad daca sunt client si e rezervarea mea asta pot sa fac si din front end
    @DeleteMapping("/rezervari")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<String> deleteRezervare(
            @RequestParam(value = "id") String id,
            @RequestParam(value = "idTeren") String idTeren,
            @RequestParam(value = "emailClient") String emailClient,
            @RequestParam(value = "emailOwner") String emailOwner,
            @RequestParam(value = "interval") String interval,
            @RequestParam(value = "data") String data
    ) {
        rezervareRepository.delete(rezervareRepository.findAllById(id));
        // TODO: email si token
        return ResponseEntity.ok().body("Deleted");

    }

    // aici modific tabela de rezervare a terenului + la FrontEnd sa vad daca am avut succes
    // la adaugare, daca da fac si asta, daca nu, nu mai fac
    @PutMapping("/rezervariTeren/{action}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public RezervareTeren postRezervareTeren(
            @RequestParam(value = "idTeren") String idTeren,
            @RequestParam(value = "interval") String interval,
            @RequestParam(value = "data") String data,
            @PathVariable String action
    ) {
       RezervareTeren rezervareTeren = rezervareTerenRepository.findByIdTeren(idTeren);
       boolean val = true;
       if(action.equals("delete")) {
           val = false;
       }
       if(rezervareTeren != null) {
            rezervareTeren.setId(idTeren);
            HashMap<String, HashMap<Integer, Boolean>> date = rezervareTeren.getIntervale();
            if(date.containsKey(data)) {
                int start = Integer.parseInt(interval.split("-")[0]);
                int stop = Integer.parseInt(interval.split("-")[1]);
                for(int i = start; i < stop; ++i) {
                    rezervareTeren.getIntervale().get(data).put(i, val);
                }
            } else {
                HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>();
                for(int i=0; i<24; ++i) {
                    map.put(i, false);
                }
                rezervareTeren.getIntervale().put(data, map);
                int start = Integer.parseInt(interval.split("-")[0]);
                int stop = Integer.parseInt(interval.split("-")[1]);
                for(int i = start; i < stop; ++i) {
                    rezervareTeren.getIntervale().get(data).put(i, val);
                }
            }
            return rezervareTerenRepository.save(rezervareTeren);
        } else {
            rezervareTeren = new RezervareTeren(idTeren);
            rezervareTeren.setId(idTeren);
            HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>();
            for(int i=0; i<24; ++i) {
                map.put(i, false);
            }
            rezervareTeren.getIntervale().put(data, map);
            int start = Integer.parseInt(interval.split("-")[0]);
            int stop = Integer.parseInt(interval.split("-")[1]);
            for(int i = start; i < stop; ++i) {
                rezervareTeren.getIntervale().get(data).put(i, val);
            }
        }
        return rezervareTerenRepository.save(rezervareTeren);
    }


}
