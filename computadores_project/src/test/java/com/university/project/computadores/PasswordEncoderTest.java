package com.university.project.computadores;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "admin";
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("Senha original: " + rawPassword);
        System.out.println("Senha codificada: " + encodedPassword);
        
        // Verificar se a senha corresponde
        boolean matches = encoder.matches(rawPassword, encodedPassword);
        System.out.println("A senha corresponde? " + matches);
    }
} 