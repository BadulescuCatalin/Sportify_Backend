package com.example.mongodb.repository;

import com.example.mongodb.model.Echipa;
import com.example.mongodb.model.Rezervare;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EchipaRepository extends MongoRepository<Echipa, String> {
    Echipa findAllById(String id);
    List<Echipa> findAll();
}
