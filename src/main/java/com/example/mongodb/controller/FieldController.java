package com.example.mongodb.controller;

import com.example.mongodb.Services.DocumentService;
import com.example.mongodb.Services.EmailService;
import com.example.mongodb.model.Field;
import com.example.mongodb.repository.FieldRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

// am sters eu un fields la requestmapping
@RestController
public class FieldController {

    @Autowired
    private FieldRepository fieldRepository;
    @Autowired
    private final EmailService emailService = null;


    @GetMapping("/fields")
    public ResponseEntity<List<Field>> getAllFields() {
        List<Field> fields = fieldRepository.findAllWithoutFileData();
        return ResponseEntity.ok().body(fields);
    }

    public Field getFieldById(String id) {
        return fieldRepository.findById(id).orElse(null);
    }

    // La asta trebuie facut requestul altfel
    // TODO: de fandit cum facem verificare teren unic
    @PostMapping("/fields")
    public ResponseEntity<String> createField(
            @RequestParam("owner") String owner, @RequestParam("city") String city,
            @RequestParam("address") String address, @RequestParam("description") String description,
            @RequestParam("price") Integer price, @RequestParam("basketball") Boolean basketball,
            @RequestParam("football") Boolean football, @RequestParam("tennis") Boolean tennis,
            @RequestParam("fileData") MultipartFile fileData) throws IOException {




        Field field = new Field();
        field.setOwner(owner);
        field.setCity(city);
        field.setAddress(address);
        field.setDescription(description);
        field.setPrice(price);
        field.setBasketball(basketball);
        field.setTennis(tennis);
        field.setFootball(football);
        field.setFileData(new Binary(BsonBinarySubType.BINARY, fileData.getBytes()));
        fieldRepository.save(field);
        emailService.sendMailWithAttachment("badulescucatalin01@gmail.com", "Please verify this ownership proof",
                "The owner with the username: " + owner + " wants to verify his ownership proof", field.getFileData().getData(), owner + "'s proof");
        return ResponseEntity.ok().body("Field added");
    }

    // de facut altfel de request
    @PutMapping("/fields")
    public Field updateFieldById(
            @RequestParam("id") String id, @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "price", required = false) Integer price,
            @RequestParam(value = "basketball", required = false) Boolean basketball,
            @RequestParam(value = "football", required = false) Boolean football,
            @RequestParam(value = "tennis", required = false) Boolean tennis,
            @RequestParam(value = "fileData", required = false) MultipartFile fileData) throws IOException {
        Field field = this.getFieldById(id);
        if(!city.equals("")) {
            field.setCity(city);
        }
        if(!address.equals("")) {
            field.setAddress(address);
        }
        if(!description.equals("")) {
            field.setDescription(description);
        }
        if(price != null) {
            field.setPrice(price);
        }
        if (basketball != null) {
            field.setBasketball(basketball);
        }
        if (football != null) {
            field.setFootball(football);
        }
        if (tennis != null) {
            field.setTennis(tennis);
        }
        if (fileData != null) {
            field.setFileData(new Binary(BsonBinarySubType.BINARY, fileData.getBytes()));
        }
        return fieldRepository.save(field);
    }

    @DeleteMapping("/fields")
    public String deleteFieldById(@RequestBody Map<String, String> request) {
        String id = request.get("id");
        fieldRepository.deleteById(id);
        return "Field with id " + id + " has been deleted!";
    }

    @GetMapping("/owner/{owner}")
    public List<Field> getFieldsByOwner(@PathVariable String owner) {
        return fieldRepository.findByOwner(owner);
    }


    @GetMapping("/sport/{sport}")
    public List<Field> getFieldsBySport(@PathVariable String sport) {
        switch(sport.toLowerCase()) {
            case "basketball":
                return fieldRepository.findByBasketballTrue();
            case "football":
                return fieldRepository.findByFootballTrue();
            case "tennis":
                return fieldRepository.findByTennisTrue();
            default:
                return null;
        }
    }

//    @GetMapping("/sport/sorted/{sortOrder}")
//    public List<Field> getFieldsSortedBySport(@PathVariable String sortOrder) {
//        Sort sort;
//        if (sortOrder.equalsIgnoreCase("desc")) {
//            sort = Sort.by(Sort.Direction.DESC, "sport");
//        } else {
//            sort = Sort.by(Sort.Direction.ASC, "sport");
//        }
//        return fieldRepository.findAll(sort);
//    }

    @GetMapping("/city/{city}")
    public List<Field> getFieldsByCity(@PathVariable String city) {
        return fieldRepository.findByCity(city);
    }

    @GetMapping("/city/sorted/{sortOrder}")
    public List<Field> getFieldsSortedByCity(@PathVariable String sortOrder) {
        Sort sort;
        if (sortOrder.equalsIgnoreCase("desc")) {
            sort = Sort.by(Sort.Direction.DESC, "city");
        } else {
            sort = Sort.by(Sort.Direction.ASC, "city");
        }
        return fieldRepository.findAll(sort);
    }

    @GetMapping("/price/{sortOrder}")
    public List<Field> getFieldsByPrice(@PathVariable String sortOrder) {
        Sort sort;
        if (sortOrder.equalsIgnoreCase("desc")) {
            sort = Sort.by(Sort.Direction.DESC, "price");
        } else {
            sort = Sort.by(Sort.Direction.ASC, "price");
        }
        return fieldRepository.findAll(sort);
    }




    // TODO: sa ma gandesc daca ar trebui sa mai pun pe undeva path variable cum e aici la sporturi!!!!!!!
    // TODO: de sortat dupa sporturi si de filtrat dupa sporturi
    // TODO: de sortat dupa pret
    // TODO: de sortat dupa oras si de filtrat dupa oras

}
