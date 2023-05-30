package com.example.mongodb.controller;

import com.example.mongodb.encrypt_decrypt.PasswordEncryptorDecryptor;
import com.example.mongodb.model.Echipa;
import com.example.mongodb.model.Rezervare;
import com.example.mongodb.repository.EchipaRepository;
import com.example.mongodb.repository.RezervareRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.List;

@RestController
public class EchipaController {
    // La toate de verificat token!!!!!!
    @Autowired
    EchipaRepository echipaRepository;

    @GetMapping("/echipe")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public List<Echipa> getEchipe() {
        return echipaRepository.findAll();
    }

    //Todo: adauga echipa ca si capitan; token + email

    @PostMapping("/echipe")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Echipa> adaugaEchipa(@RequestHeader("Authorization") String token,
                                               @RequestParam(value = "numeEchipa") String numeEchipa,
                                               @RequestParam(value = "descriereEchipa") String descriereEcipa,
                                               @RequestParam(value = "nrMembriActuali") int nrMembriActuali,
                                               @RequestParam(value = "numarMembriDoriti") int numarMembriDoriti,
                                               @RequestParam(value = "emailCapitan") String emailCapitan) {
        try {
            byte[] keyBytes = (new PasswordEncryptorDecryptor().getSecretKeyForSigning()).getBytes();
            SecretKey secretKey = Keys.hmacShaKeyFor(keyBytes);
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            // Extract the subject (email) from the claims
            String role = claims.get("role", String.class);
            if (role.equals("owner") || role.equals("client")) {
                Echipa echipa = new Echipa(numeEchipa, descriereEcipa, nrMembriActuali, numarMembriDoriti, emailCapitan);
                echipaRepository.save(echipa);
                return ResponseEntity.ok().body(echipa);
            } else {
                return ResponseEntity.ok().body(null);
            }

        } catch (JwtException e) {
            System.out.println(e);
            // Invalid token or signature verification failed
            // Handle the exception appropriately
            return ResponseEntity.ok().body(null);
        }
    }

    //TODO: sterge echipa ca si capitan + token si email

    @DeleteMapping("/echipe")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<String> stergeEchipa(@RequestParam(value = "id") String id) {
        echipaRepository.delete(echipaRepository.findAllById(id));
        return ResponseEntity.ok().body("echipa stersa");
    }

    //TODO: intrare in echipa ca membru singur sau ca si capitan alta echipa. Daca e full nu pot intra sau daca devine full nu mai e accesibila la altii
    // + TOKEN
    @PutMapping("/echipe/add/{id}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<String> adaugaMembri(@PathVariable String id,
                                               @RequestParam(value = "nrMembri") int nrMembri,
                                               @RequestParam(value = "email") String email
                                                ) {
        String emailTemp = email.replaceAll("\\.", ",");
        Echipa echipa = echipaRepository.findAllById(id);
        echipa.getEmailuriParticipant().put(emailTemp, nrMembri);
        if(echipa.getNrMembriActuali() + nrMembri == echipa.getNumarMembriDoriti()) {
            // de trimis email ca echipa este full si la capitan si la celalalt
            echipa.setNrMembriActuali(echipa.getNrMembriActuali() + nrMembri);
            echipaRepository.save(echipa);
            return ResponseEntity.ok().body("echipa full");
        }
        // de trimis email la amandoi
        echipa.setNrMembriActuali(echipa.getNrMembriActuali() + nrMembri);
        echipaRepository.save(echipa);
        return ResponseEntity.ok().body("adaugat");
    }
    //TODO: sa pot sa ma dezinscriu de la echipa singur sau cu toata echipa daca eram capitan
    @PutMapping("/echipe/remove/{id}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<String> removeMembri(@PathVariable String id,
                                               @RequestParam(value = "email") String email
    ) {
        String emailTemp = email.replaceAll("\\.", ",");
        Echipa echipa = echipaRepository.findAllById(id);
        if(email.equals(echipa.getEmailCapitan())) {
            echipaRepository.delete(echipa);
            return ResponseEntity.ok().body("echipa stearsa");
        }
        int nrMemb = echipa.getEmailuriParticipant().get(emailTemp);
        // de trimis email la amandoi
        echipa.getEmailuriParticipant().remove(emailTemp);
        echipa.setNrMembriActuali(echipa.getNrMembriActuali() - nrMemb);
        echipaRepository.save(echipa);
        return ResponseEntity.ok().body("substras");
    }
}
