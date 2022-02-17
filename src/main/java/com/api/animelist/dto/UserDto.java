package com.api.animelist.dto;

import com.api.animelist.models.ListaAnimesModel;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class UserDto {

    @NotBlank
    private String nome;
    @NotBlank
    private String dataNasc;
    @NotBlank
    private String email;
    @NotBlank
    private String senha;
    private List<ListaAnimesModel> listaAnimes;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<ListaAnimesModel> getListaAnimes() {
        return listaAnimes;
    }

    public void setListaAnimes(List<ListaAnimesModel> listaAnimes) {
        this.listaAnimes = listaAnimes;
    }
}
