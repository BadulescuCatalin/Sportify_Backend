package com.example.mongodb.repository;

import com.example.mongodb.model.Field;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldRepository extends MongoRepository<Field, String> {

    @Query(value = "{}", fields = "{ 'fileData' : 0 }")
    List<Field> findAllWithoutFileData();

    List<Field> findByBasketballTrue();

    List<Field> findByFootballTrue();

    List<Field> findByTennisTrue();


}



