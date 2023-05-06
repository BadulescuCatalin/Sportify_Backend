package com.example.mongodb.controller;

import com.example.mongodb.encrypt_decrypt.PasswordEncryptorDecryptor;
import com.example.mongodb.model.Account;
import com.example.mongodb.accountRepository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
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

    public HashSet<Account> getAllAccounts(){
        return new HashSet<>(ResponseEntity.ok(this.repository.findAll()).getBody());
    }

    @GetMapping("/accountsEmails")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public HashSet<String> getAllEmails(){
        return ResponseEntity.ok(this.repository.findAll()).getBody().stream().map(acc->acc.getEmail()).collect(Collectors.toCollection(HashSet:: new));
    }

    public HashSet<String> getAllUserNames(){
        return ResponseEntity.ok(this.repository.findAll()).getBody().stream().map(acc->acc.getUserName()).collect(Collectors.toCollection(HashSet:: new));
    }

    @DeleteMapping("/accounts")
    public void deleteUser(Account acc) {
        this.repository.delete(acc);
    }

    @PostMapping("/accounts")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<String> registerAccount(@RequestBody Account accountRequest){
        HashSet<String> existingEmails = getAllEmails();
        HashSet<String> existingUserNames = getAllUserNames();
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

    @GetMapping("/login")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<String> logIn(@RequestBody Account accountRequest){
        HashSet<Account> existingAccounts = getAllAccounts();
        HashSet<String> existingEmails = getAllEmails();
        String email = accountRequest.getEmail();
        if(existingAccounts.contains(accountRequest)) {
            return ResponseEntity.ok().body("User logged in successfuly");
        } else if (!existingEmails.contains(email)){
            return ResponseEntity.ok().body("There is no user associated with this email");
        } else {
            return ResponseEntity.ok().body("Wrong password");
        }
    }
}