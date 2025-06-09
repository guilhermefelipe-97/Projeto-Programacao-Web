package com.university.project.computadores.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Representa a entidade Computador no banco de dados.
 * Esta classe contém os atributos de um computador e as validações
 * necessárias para garantir a integridade dos dados.
 * Getters, setters e construtores implementados manualmente para evitar problemas com Lombok.
 */
@Entity
public class Computador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do computador não pode estar em branco.")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "A marca do computador não pode estar em branco.")
    @Column(nullable = false)
    private String marca;

    @NotBlank(message = "O processador não pode estar em branco.")
    @Column(nullable = false)
    private String processador;

    @NotBlank(message = "A memória RAM não pode estar em branco.")
    @Column(nullable = false)
    private String memoriaRam;

    @NotBlank(message = "O armazenamento não pode estar em branco.")
    @Column(nullable = false)
    private String armazenamento;

    @NotNull(message = "O preço não pode ser nulo.")
    @Positive(message = "O preço deve ser um valor positivo.")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(nullable = true, length = 1000)
    private String descricao;

    @NotBlank(message = "A URL da imagem não pode estar em branco.")
    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = true)
    private Long isDeleted = null;

    // Construtor sem argumentos (necessário para JPA)
    public Computador() {
    }

    // Construtor com todos os argumentos (opcional, mas pode ser útil)
    public Computador(Long id, String nome, String marca, String processador, String memoriaRam, String armazenamento, BigDecimal preco, String descricao, String imageUrl, Long isDeleted) {
        this.id = id;
        this.nome = nome;
        this.marca = marca;
        this.processador = processador;
        this.memoriaRam = memoriaRam;
        this.armazenamento = armazenamento;
        this.preco = preco;
        this.descricao = descricao;
        this.imageUrl = imageUrl;
        this.isDeleted = isDeleted;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getProcessador() {
        return processador;
    }

    public void setProcessador(String processador) {
        this.processador = processador;
    }

    public String getMemoriaRam() {
        return memoriaRam;
    }

    public void setMemoriaRam(String memoriaRam) {
        this.memoriaRam = memoriaRam;
    }

    public String getArmazenamento() {
        return armazenamento;
    }

    public void setArmazenamento(String armazenamento) {
        this.armazenamento = armazenamento;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Long isDeleted) {
        this.isDeleted = isDeleted;
    }

    // equals e hashCode (gerados pela IDE ou manualmente, baseados no ID)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Computador that = (Computador) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // toString (opcional, mas útil para debug)
    @Override
    public String toString() {
        return "Computador{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", marca='" + marca + '\'' +
                // ... (adicionar outros campos se desejar)
                '}';
    }
}