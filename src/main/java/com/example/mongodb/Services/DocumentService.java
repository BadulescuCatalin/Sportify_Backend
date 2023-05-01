package com.example.mongodb.Services;

import com.example.mongodb.model.Documents;
import com.example.mongodb.repository.DocumentsRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class DocumentService {
    @Autowired
    private DocumentsRepository documentsRepository;

    public String addDoc(String name, String owner, MultipartFile file) throws IOException {
        Documents doc = new Documents(owner, name, new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        documentsRepository.insert(doc);
        return doc.getId();
    }

    public Documents getDoc(String id) {
        return documentsRepository.findById(id).get();
    }
}
