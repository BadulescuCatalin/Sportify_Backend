package com.example.mongodb.controller;

import com.example.mongodb.model.Field;
import com.example.mongodb.repository.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fields")
public class FieldController {

    @Autowired
    private FieldRepository fieldRepository;

    @GetMapping("/")
    public List<Field> getAllFields() {
        return fieldRepository.findAll();
    }

    @GetMapping("/{id}")
    public Field getFieldById(@PathVariable String id) {
        return fieldRepository.findById(id).orElse(null);
    }

    @PostMapping("/")
    public Field createField(@RequestBody Field field) {
        return fieldRepository.save(field);
    }

    @PutMapping("/{id}")
    public Field updateFieldById(@PathVariable String id, @RequestBody Field field) {
        field.setId(id);
        return fieldRepository.save(field);
    }

    @DeleteMapping("/{id}")
    public String deleteFieldById(@PathVariable String id) {
        fieldRepository.deleteById(id);
        return "Field with id " + id + " has been deleted!";
    }

    @GetMapping("/sport/{sport}")
    public List<Field> getFieldsBySport(@PathVariable String sport) {
        switch(sport.toLowerCase()) {
            case "basketball":
                return fieldRepository.findByBasketballTrue();
            case "football":
                return fieldRepository.findByFootballTrue();
            case "tenis":
                return fieldRepository.findByTenisTrue();
            case "handball":
                return fieldRepository.findByHandballTrue();
            default:
                return null;
        }
    }

    @GetMapping("/feature/{feature}")
    public List<Field> getFieldsByFeature(@PathVariable String feature) {
        switch(feature.toLowerCase()) {
            case "night_lights":
                return fieldRepository.findByNight_LightsTrue();
            case "synthetic":
                return fieldRepository.findBySyntheticTrue();
            case "indoor_cover":
                return fieldRepository.findByIndoor_CoverTrue();
            default:
                return null;
        }
    }
}
