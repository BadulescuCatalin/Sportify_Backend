package com.example.mongodb.FieldRepository;

import com.example.mongodb.Field.Field;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldRepository extends MongoRepository<Field, String> {
    List<Field> findByBasketballTrue();
    List<Field> findByFootballTrue();
    List<Field> findByTenisTrue();
    List<Field> findByHandballTrue();
    List<Field> findByNight_LightsTrue();
    List<Field> findBySyntheticTrue();
    List<Field> findByIndoor_CoverTrue();
}


