package com.example.mongodb.repository;

import com.example.mongodb.model.Documents;
import org.springframework.data.mongodb.repository.MongoRepository;


@org.springframework.stereotype.Repository
public interface DocumentsRepository extends MongoRepository<Documents, String> {
}
