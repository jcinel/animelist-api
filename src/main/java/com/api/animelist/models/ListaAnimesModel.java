package com.api.animelist.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="ListaAnimes")
public class ListaAnimesModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ListaAnimesKey id;
    @Column
    private float nota;
    @Column(nullable = false, length = 50)
    private String status;
    @ManyToOne
    @MapsId("IdAnime")
    @JoinColumn(name = "ID_Anime")
    private AnimeModel anime;
    @ManyToOne
    @MapsId("IdUsuario")
    @JoinColumn(name = "ID_Usuario")
    private UserModel usuario;

    public ListaAnimesKey getId() {
        return id;
    }

    public void setId(ListaAnimesKey id) {
        this.id = id;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AnimeModel getAnime() {
        return anime;
    }

    public void setAnime(AnimeModel anime) {
        this.anime = anime;
    }

    public UserModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UserModel usuario) {
        this.usuario = usuario;
    }
}