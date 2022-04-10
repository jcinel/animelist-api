package com.api.animelist.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="Animes")
public class AnimeModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, unique = true, length = 50)
    private String nome;

    @Column(nullable = false, length = 50)
    private String autor;

    @Column(length = 500)
    private String sinopse;

    @OneToMany(mappedBy = "animeModel")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<ListaAnimesModel> listaAnimes;

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

    public String getAutor() {
        return autor;
    }

    public List<ListaAnimesModel> getListaAnimes() {
        return listaAnimes;
    }

    public void setListaAnimes(List<ListaAnimesModel> listaAnimes) {
        this.listaAnimes = listaAnimes;
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
