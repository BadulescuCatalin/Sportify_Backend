package com.example.mongodb.controller;

import com.example.mongodb.encrypt_decrypt.PasswordEncryptorDecryptor;
import com.example.mongodb.model.Account;
import com.example.mongodb.repository.Repository;
import com.mongodb.client.*;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

//@Service
@RestController
public class AccountController {

    @Autowired()
    private final Repository repository = null;

    MongoClient mongoClient = MongoClients.create("mongodb+srv://user1:Parola@cluster0.6xqwmee.mongodb.net/?retryWrites=true&w=majority");
    MongoDatabase database = mongoClient.getDatabase("sportif");
    MongoCollection<Document> collection = database.getCollection("accounts");

    @Autowired
    private MongoTemplate mongoTemplate;
    @GetMapping("/accounts")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public HashSet<Account> getAllAccounts(){
        return new HashSet<>(ResponseEntity.ok(this.repository.findAll()).getBody());
    }

    @GetMapping("/accountsEmails")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public HashSet<String> getAllEmails(){
        return ResponseEntity.ok(this.repository.findAll()).getBody().stream().map(acc->acc.getEmail()).collect(Collectors.toCollection(HashSet:: new));
    }

    @GetMapping("/accounts/userNames")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public HashSet<String> getAllUserNames(){
        return ResponseEntity.ok(this.repository.findAll()).getBody().stream().map(acc->acc.getUserName()).collect(Collectors.toCollection(HashSet:: new));
    }

    @GetMapping("/accounts/{email}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Account> findAccountWithEmail(@PathVariable(value = "email") String email) {

        Query query = new Query().addCriteria(Criteria.where("email").is(email));
        Account acc = mongoTemplate.findOne(query, Account.class);
        if(acc != null)
            return new ResponseEntity<>(mongoTemplate.findOne(query, Account.class), HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/accounts/{usernName}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Account> findAccountWithUsername(@PathVariable(value = "userName") String userName) {

        Query query = new Query().addCriteria(Criteria.where("userName").is(userName));
        Account acc = mongoTemplate.findOne(query, Account.class);
        if(acc != null)
            return new ResponseEntity<>(mongoTemplate.findOne(query, Account.class), HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/accounts")
    public void deleteUser(Account acc) {
        this.repository.delete(acc);
    }


    @GetMapping("/emails")
    public HashSet<String> getAllEmails1() {
        HashSet<String> list = this.repository.findAllUserEmails();
        return list.stream().map(s -> s.replaceAll("\\{\"email\": \"|\"\\}", ""))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toCollection(HashSet::new));
    }

    @GetMapping("/userNames")
    public HashSet<String> getAllUserNames1() {
        HashSet<String> list = this.repository.findAllUsernames();
                return list.stream().map(s -> s.replaceAll("\\{\"userName\": \"|\"\\}", ""))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toCollection(HashSet::new));
    }

    @PostMapping("/accounts")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<String> registerAccount(@RequestBody Account accountRequest){
        HashSet<String> existingEmails = getAllEmails1();
        HashSet<String> existingUserNames = getAllUserNames1();
        Account account = new Account();
        String email = accountRequest.getEmail();
        String userName = accountRequest.getUserName();
        String password = accountRequest.getPassword();
        if(existingEmails.contains(email)) {
            return ResponseEntity.ok().body("Email already used");
        } else if(existingUserNames.contains(userName)){
            return ResponseEntity.ok().body("Username already used");
        } else {
            account.setEmail(email);
            account.setPassword(PasswordEncryptorDecryptor.encrypt(password));
            account.setUserName(accountRequest.getUserName());
            account.setRole(accountRequest.getRole());
            this.repository.save(account);
            return ResponseEntity.ok().body("User registered");
        }
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<String> logIn(@RequestBody Map<String, String> credentials ){
        String email = credentials.get("email");
        String password = credentials.get("password");

        Account acc = findAccountWithEmail(email).getBody();
        if(acc == null) {
            return ResponseEntity.ok().body("Wrong credentials");
        } else {
            if(PasswordEncryptorDecryptor.encrypt(password).equals(acc.getPassword())) {
                // Create the JWT token
                Date expirationDate = new Date(System.currentTimeMillis() + 86400000); // 1 day in milliseconds

                // Convert the key to bytes for signing
                byte[] keyBytes = (new PasswordEncryptorDecryptor().getSecretKeyForSigning()).getBytes();
                SecretKey secretKey = Keys.hmacShaKeyFor(keyBytes);
                Map<String, Object> map = new HashMap<>();
                map.put("role", acc.getRole());
                map.put("username", acc.getUserName());

                String token = Jwts.builder()
                        .setSubject(email)
                        .addClaims(map)
                        //.claim("owner/client", acc.getRole())
                        .setExpiration(expirationDate)
                        .signWith(secretKey, SignatureAlgorithm.HS256)
                        .compact();

                // Return the JWT token in the response
                return ResponseEntity.ok().header("Authorization", "Bearer " + token).body(token);
            } else {
                return ResponseEntity.ok().body("Wrong credentials");
            }
        }
//        HashSet<Account> existingAccounts = getAllAccounts();
//        HashSet<String> existingEmails = getAllEmails1();
//        for(Account acc : existingAccounts) {
//            if(acc.getEmail().equals(email) && PasswordEncryptorDecryptor.encrypt(acc.getPassword()).equals(password)) {
//                return new ResponseEntity<>("User logged in successfuly", HttpStatus.OK);
//            }
//        }
//        if (!existingEmails.contains(email)){
//            return ResponseEntity.ok().body("There is no user associated with this email");
//        } else {
//            return ResponseEntity.ok().body("Wrong password");
//        }
    }

}