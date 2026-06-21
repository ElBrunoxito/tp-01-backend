package com.yobrunox.tp01backendhwt.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KeyUtil {
    @Value("${app.security.db-key}")
    public static String encryptionKey;

    @PostConstruct
    public void setEncryptionKey() {
        // Esto establece la variable a nivel de sesión en PostgreSQL
        // para las consultas que se ejecuten después
    }
}
