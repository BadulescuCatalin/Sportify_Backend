package com.example.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.mongodb.model.Account;
import org.springframework.data.mongodb.repository.Query;

import java.util.HashSet;

@org.springframework.stereotype.Repository
public interface Repository extends MongoRepository<Account, String> {
    @Query(value = "{}", fields = "{ email : 1, '_id': 0 }")
    HashSet<String> findAllUserEmails();
    @Query(value = "{}", fields = "{ userName : 1, '_id': 0 }")
    HashSet<String> findAllUsernames();
}