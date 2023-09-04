package com.example.mongodb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "documents")
@JsonIgnoreProperties(value = { "_id" }, allowGetters = true)
public class Documents {
    @Id
    private String id;
    private String owner;
    private Binary fileData;

    public String getId() {
        return id;
    }

    public Documents(String owner, String name, Binary fileData) {
        this.fileData = fileData;
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public Binary getFileData() {
        return fileData;
    }
}
