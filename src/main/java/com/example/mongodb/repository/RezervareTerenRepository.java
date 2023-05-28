package com.example.mongodb.repository;

import com.example.mongodb.model.Documents;
import com.example.mongodb.model.RezervareTeren;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface RezervareTerenRepository extends MongoRepository<RezervareTeren, String> {
    RezervareTeren findByIdTeren(String idTeren);
}
