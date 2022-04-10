package com.api.animelist.dto;

import javax.validation.constraints.NotBlank;

public class AnimeDto {

    @NotBlank
    private String nome;
    @NotBlank
    private String autor;

    private String sinopse;

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

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }
}
