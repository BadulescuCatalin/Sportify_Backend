package com.example.mongodb.repository;

import com.example.mongodb.model.Documents;
import com.example.mongodb.model.Rezervare;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface RezervareRepository extends MongoRepository<Rezervare, String> {
    Rezervare findAllById(String id);
    List<Rezervare> findAll();
    List<Rezervare> findAllByEmailClient(String emailClient);
    List<Rezervare> findAllByEmailOwner(String emailOwner);
}
