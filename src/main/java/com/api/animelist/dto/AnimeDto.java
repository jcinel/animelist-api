package com.api.animelist.dto;

import javax.validation.constraints.NotBlank;

public class AnimeDto {

    @NotBlank
    private String nome;
    @NotBlank
    private String autor;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}
