package com.example.mongodb.controller;

import com.example.mongodb.encrypt_decrypt.PasswordEncryptorDecryptor;
import com.example.mongodb.model.Account;
import com.example.mongodb.repository.Repository;
import com.example.mongodb.resource.AccountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class AccountController {

    @Autowired()
    private final Repository repository = null;
//   // public AccountController(Repository repository){
//        this.repository = repository;
//    }
    @GetMapping("/accounts")
    @CrossOrigin(origins = "*", maxAge = 3600)

    public ResponseEntity<List<Account>> getAllAccounts(){
        return ResponseEntity.ok(this.repository.findAll());
    }


    @GetMapping("/accountsEmails")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public List<String> getAllEmails(){
        return ResponseEntity.ok(this.repository.findAll()).getBody().stream().map(acc->acc.getEmail()).collect(Collectors.toCollection(ArrayList::new));
    }

    @DeleteMapping("/accounts")
    public void deleteUser(Account acc) {
        this.repository.delete(acc);
    }

    @PostMapping("/accounts")
    @CrossOrigin(origins = "*", maxAge = 3600)

    public ResponseEntity<Account> createAccount(@RequestBody AccountRequest accountRequest){

        ResponseEntity<List<Account>> existingAccounts = getAllAccounts();
//        for(Account acc : existingAccounts.getBody()) {
//            System.out.println(acc.getEmail());
//        }
        Account account = new Account();
        account.setEmail(accountRequest.getEmail());
        account.setPassword(PasswordEncryptorDecryptor.encrypt(accountRequest.getPassword()));
        return ResponseEntity.status(201).body(this.repository.save(account));
    }
}
