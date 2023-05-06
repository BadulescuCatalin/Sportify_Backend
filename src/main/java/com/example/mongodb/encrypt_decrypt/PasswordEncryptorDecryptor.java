package com.example.mongodb.encrypt_decrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.tomcat.util.codec.binary.Base64;

public class PasswordEncryptorDecryptor {

    private static final String secretKey = "mySecretKey12345"; // 128 bit key
    private static final String initVector = "encryptionIntVec"; // 16 bytes IV
    private static final String secretKeyForSigning = "D6C417240AF0429FA0DC45861F7A90B344BEEB2F3FA66328FF7B3AED0598E0D8"; // 128 bits key

    public static String encrypt(String password) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(password.getBytes());
            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String encryptedPassword) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encryptedPassword));
            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String getSecretKeyForSigning() {
        return secretKeyForSigning;
    }

}

