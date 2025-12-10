package com.Carpeta_Programas_Java.Archivos_Java;

import com.Carpeta_Programas_Java.Archivos_Java.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.Instant;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Usuario {

    private String email;
    private String handle;
    private Instant createdAt;
    private String bio;

    public Usuario() {
    }

    public Usuario(String email, String handle, Instant createdAt, String bio) {
        this.email = email;
        this.handle = handle;
        this.createdAt = createdAt;
        this.bio = bio;
    }

    public String email() {
        return email;
    }

    public String handle() {
        return handle;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public String bio() {
        return bio;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "email='" + email + '\'' +
                ", handle='" + handle + '\'' +
                '}';
    }

    // Identidad: email
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario usuario)) return false;
        return Objects.equals(email, usuario.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
