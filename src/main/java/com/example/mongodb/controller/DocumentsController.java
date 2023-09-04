package com.example.mongodb.controller;

import com.example.mongodb.Services.DocumentService;
import com.example.mongodb.Services.EmailService;
import com.example.mongodb.model.Documents;
import com.example.mongodb.repository.DocumentsRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@RestController
public class DocumentsController {

    MongoClient mongoClient = MongoClients.create("mongodb+srv://user1:Parola@cluster0.6xqwmee.mongodb.net/?retryWrites=true&w=majority");
    MongoDatabase database = mongoClient.getDatabase("sportif");

    @Autowired()
    private final DocumentService documentService = null;

    @Autowired
    private final EmailService emailService = null;

    @PostMapping("/documents/add")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public String downloadImg(@RequestParam("owner") String owner,
                                        @RequestParam("name") String name,
                                        @RequestParam("fileData") MultipartFile file) throws IOException {
        //file.getName(); -> de pus mai incolo
        String id = documentService.addDoc(owner, name, file);
        Documents doc = documentService.getDoc(id);
        emailService.sendMailWithAttachment("badulescucatalin01@gmail.com", "Please verify this ownership proof",
                "The owner with the username: " + owner + " wants to verify his ownership proof", doc.getFileData().getData(), doc.getOwner() + "'s proof");
        return id;

        //        MongoCollection<Document> collection = database.getCollection("files");
//
//        // create a new document with the file content and metadata
//        Document document = new Document();
//        document.append("name", file.getOriginalFilename());
//        document.append("content", file.getBytes());
    }

    @GetMapping("/documents/{id}")
    public ResponseEntity<byte[]> getDoc(@PathVariable String id, Model model) {
        Documents doc = documentService.getDoc(id);
        model.addAttribute("name", doc.getOwner() + "'s proof");
        model.addAttribute("Owner", doc.getOwner());
        model.addAttribute("fileData",
                Base64.getEncoder().encodeToString(doc.getFileData().getData()));
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG).body(doc.getFileData().getData());
    }

}
