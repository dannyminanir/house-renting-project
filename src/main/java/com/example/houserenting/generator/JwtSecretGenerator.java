package com.example.houserenting.generator;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class JwtSecretGenerator {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        keyGen.init(256); // Key size
        String secret = Base64.getEncoder().encodeToString(keyGen.generateKey().getEncoded());
        System.out.println("JWT Secret: " + secret);
    }
}