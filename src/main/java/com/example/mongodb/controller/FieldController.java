package com.example.mongodb.controller;

import com.example.mongodb.Services.DocumentService;
import com.example.mongodb.Services.EmailService;
import com.example.mongodb.encrypt_decrypt.PasswordEncryptorDecryptor;
import com.example.mongodb.model.Field;
import com.example.mongodb.model.Field2;
import com.example.mongodb.repository.FieldRepository;
import com.example.mongodb.repository.Repository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

// am sters eu un fields la requestmapping
@RestController
public class FieldController {

    @Autowired
    private FieldRepository fieldRepository;
    @Autowired
    private final EmailService emailService = null;
    @Autowired
    private final Repository repository = null;


    @GetMapping("/fields")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<List<Field>> getAllFields() {
        List<Field> fields = fieldRepository.findAllWithoutFileData();
        return ResponseEntity.ok().body(fields);
    }

    @GetMapping("/fields2")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<List<Field>> getAllFields2() {
        List<Field> fields = fieldRepository.findAll();
        return ResponseEntity.ok().body(fields);
    }

    @GetMapping("/id/{id}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public Field getFieldById(@PathVariable String id) {
        return fieldRepository.findById(id).orElse(null);
    }

    // La asta trebuie facut requestul altfel
    // TODO: de fandit cum facem verificare teren unic
    @PostMapping("/fields")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<String> createField(@RequestHeader("Authorization") String token,
                                              @RequestParam(value = "owner") String owner,
                                              @RequestParam(value = "city") String city,
                                              @RequestParam(value = "address") String address,
                                              @RequestParam(value = "description") String description,
                                              @RequestParam(value = "price") Integer price,
                                              @RequestParam(value = "basketball") Boolean basketball,
                                              @RequestParam(value = "football") Boolean football,
                                              @RequestParam(value = "tennis") Boolean tennis,
                                              @RequestParam(value = "fileData") MultipartFile fileData) throws IOException {



        try {
            byte[] keyBytes = (new PasswordEncryptorDecryptor().getSecretKeyForSigning()).getBytes();
            SecretKey secretKey = Keys.hmacShaKeyFor(keyBytes);
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            // Extract the subject (email) from the claims
            String role = claims.get("role", String.class);
            if (role.equals("owner")) {
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
            return ResponseEntity.ok().body("Field not added");

        } catch (JwtException e) {
            System.out.println(e);
            // Invalid token or signature verification failed
            // Handle the exception appropriately
            return ResponseEntity.ok().body("Field not added");
        }

    }

    // de facut altfel de request
    @PutMapping("/fields")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public Field updateFieldById(
            @RequestHeader("Authorization") String token,
            @RequestParam("id") String id,
            @RequestParam(value = "owner") String owner,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "price", required = false) Integer price,
            @RequestParam(value = "basketball", required = false) Boolean basketball,
            @RequestParam(value = "football", required = false) Boolean football,
            @RequestParam(value = "tennis", required = false) Boolean tennis,
            @RequestParam(value = "fileData", required = false) MultipartFile fileData,
            @RequestParam(value = "changed", required = false)  Boolean changed) throws IOException {

        try {
            byte[] keyBytes = (new PasswordEncryptorDecryptor().getSecretKeyForSigning()).getBytes();
            SecretKey secretKey = Keys.hmacShaKeyFor(keyBytes);
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            // Extract the subject (email) from the claims
            String role = claims.get("role", String.class);
            if (role.equals("owner")) {
                Field field = this.getFieldById(id);
                if (!city.equals("")) {
                    field.setCity(city);
                }
                if (!address.equals("")) {
                    field.setAddress(address);
                }
                if (!description.equals("")) {
                    field.setDescription(description);
                }
                if (price != null) {
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
                if(changed) {
                    emailService.sendMailWithAttachment("badulescucatalin01@gmail.com", "Please verify this ownership proof",
                            "The owner with the username: " + owner + " wants to verify his new ownership proof", field.getFileData().getData(), owner + "'s proof");

                }
                return fieldRepository.save(field);
            } else {
                return null;
            }

        } catch (JwtException e) {
            System.out.println(e);
            // Invalid token or signature verification failed
            // Handle the exception appropriately
            return null;
        }
    }

    @DeleteMapping("/fields")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public String deleteFieldById(@RequestBody Map<String, String> request) {
        String id = request.get("id");
        //fieldRepository.deleteById(id);
        fieldRepository.deleteById(id);
        return "Field with id " + id + " has been deleted!";
    }

    @GetMapping("/owner/{owner}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public List<Field> getFieldsByOwner(@PathVariable String owner) {
        return fieldRepository.findByOwner(owner);
    }

    @GetMapping("/sorted/{sort}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public List<Field> getFieldsByOwnerSorted(@PathVariable String sort) {
        List<Field> fields = fieldRepository.findAll();
        if(sort.equals("basketball")) {
            List<Field> newList = new ArrayList<>();
            for(Field f : fields) {
                if(f.getBasketball()) {
                    newList.add(f);
                }
            }
            return newList;
        } else if(sort.equals("football")){
            List<Field> newList = new ArrayList<>();
            for(Field f : fields) {
                if(f.getFootball()) {
                    newList.add(f);
                }
            }
            return newList;
        } else if(sort.equals("tennis")) {
            List<Field> newList = new ArrayList<>();
            for(Field f : fields) {
                if(f.getTennis()) {
                    newList.add(f);
                }
            }
            return newList;
        } else if(sort.equals("price asc")) {
            fields.sort(new Comparator<Field>() {
                @Override
                public int compare(Field o1, Field o2) {
                    return o1.getPrice().compareTo(o2.getPrice());
                }
            });
            return fields;
        } else if(sort.equals("price desc")){
            fields.sort(new Comparator<Field>() {
                @Override
                public int compare(Field o1, Field o2) {
                    return o2.getPrice().compareTo(o1.getPrice());
                }
            });
            return fields;
        }
        return fields;
    }


    @GetMapping("/sorted/owner/{owner}/{sort}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public List<Field> getFieldsByOwnerSorted(@PathVariable String owner, @PathVariable String sort) {
        List<Field> fields = fieldRepository.findByOwner(owner);
        if(sort.equals("basketball")) {
            List<Field> newList = new ArrayList<>();
            for(Field f : fields) {
                if(f.getBasketball()) {
                    newList.add(f);
                }
            }
            return newList;
        } else if(sort.equals("football")){
            List<Field> newList = new ArrayList<>();
            for(Field f : fields) {
                if(f.getFootball()) {
                    newList.add(f);
                }
            }
            return newList;
        } else if(sort.equals("tennis")) {
            List<Field> newList = new ArrayList<>();
            for(Field f : fields) {
                if(f.getTennis()) {
                    newList.add(f);
                }
            }
            return newList;
        } else if(sort.equals("price asc")) {
            fields.sort(new Comparator<Field>() {
                @Override
                public int compare(Field o1, Field o2) {
                    return o1.getPrice().compareTo(o2.getPrice());
                }
            });
            return fields;
        } else if(sort.equals("price desc")){
            fields.sort(new Comparator<Field>() {
                @Override
                public int compare(Field o1, Field o2) {
                    return o2.getPrice().compareTo(o1.getPrice());
                }
            });
            return fields;
        }
        return fields;
    }

    @GetMapping("/sport/{sport}")
    @CrossOrigin(origins = "*", maxAge = 3600)
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
    @CrossOrigin(origins = "*", maxAge = 3600)
    public List<Field> getFieldsByCity(@PathVariable String city) {
        return fieldRepository.findByCity(city);
    }

    @GetMapping("/city/sorted/{sortOrder}")
    @CrossOrigin(origins = "*", maxAge = 3600)
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
    @CrossOrigin(origins = "*", maxAge = 3600)
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
