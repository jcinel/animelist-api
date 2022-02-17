package com.api.animelist.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="Usuarios")
public class UserModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false, length = 50)
    private String nome;
    @Column(nullable = false, name = "datanasc")
    private String dataNasc;
    @Column(nullable = false, unique = true, length = 255)
    private String email;
    @Column(nullable = false, length = 64)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;
    @OneToMany(mappedBy = "userModel")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<ListaAnimesModel> listaAnimes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Set<ListaAnimesModel> getListaAnimes() {
        return listaAnimes;
    }

    public void setListaAnimes(Set<ListaAnimesModel> listaAnimes) {
        this.listaAnimes = listaAnimes;
    }
}
