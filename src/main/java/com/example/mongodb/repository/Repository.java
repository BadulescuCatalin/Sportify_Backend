package com.example.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.mongodb.model.Account;

public interface Repository extends MongoRepository<Account, String> {
}
