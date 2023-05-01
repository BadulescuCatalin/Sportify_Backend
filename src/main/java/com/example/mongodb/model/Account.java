package com.example.mongodb.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "accounts")
@JsonIgnoreProperties(value = { "_id" }, allowGetters = true)
public class Account {

    @Id
//    @GeneratedValue( strategy = GenerationType.IDENTITY)

    private String id;
    private String email;
    private String password;
    private String userName;
    private String role;

    public Account() {
    }

    public Account(String id, String email, String password, String userName, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.role = role;
    }

//    public String getId() {
//        return id;
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public String getRole() {
        return role;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
}

