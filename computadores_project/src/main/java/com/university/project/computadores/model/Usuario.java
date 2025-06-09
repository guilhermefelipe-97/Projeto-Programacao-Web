package com.university.project.computadores.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Representa a entidade Usuario no banco de dados.
 * Esta classe implementa UserDetails para integração com Spring Security.
 * Getters, setters, construtores e métodos UserDetails implementados manualmente.
 */
@Entity
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome de usuário não pode estar em branco.")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "A senha não pode estar em branco.")
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean isAdmin = false;

    @Column(nullable = true)
    private String nome;

    @Column(nullable = true)
    private String email;

    // Construtor sem argumentos (necessário para JPA)
    public Usuario() {
    }

    // Construtor com argumentos (opcional)
    public Usuario(Long id, String username, String password, Boolean isAdmin, String nome, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.nome = nome;
        this.email = email;
    }

    // Getters e Setters manuais
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Implementação explícita de getUsername() de UserDetails
    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Implementação explícita de getPassword() de UserDetails
    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Implementação dos métodos restantes de UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (Boolean.TRUE.equals(this.isAdmin)) { // Verificação segura de Boolean
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Lógica padrão, pode ser customizada se necessário
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Lógica padrão
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Lógica padrão
    }

    @Override
    public boolean isEnabled() {
        return true; // Lógica padrão
    }

    // equals e hashCode (gerados pela IDE ou manualmente, baseados no ID)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // toString (opcional)
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", username=\'" + username + "\'" +
                ", isAdmin=" + isAdmin +
                // Não inclua a senha no toString por segurança
                '}';
    }
}

