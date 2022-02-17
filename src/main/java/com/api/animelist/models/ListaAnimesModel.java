package com.api.animelist.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="lista_animes")
public class ListaAnimesModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    public ListaAnimesKey id;
    @Column
    private float nota;
    @Column(nullable = false, length = 50)
    private String status;

    @ManyToOne
    @MapsId("IdAnime")
    @JoinColumn(name = "id_anime")
    private AnimeModel animeModel;

    @ManyToOne
    @MapsId("IdUsuario")
    @JoinColumn(name = "id_usuario")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserModel userModel;

    public ListaAnimesModel(){
    }

    public ListaAnimesModel(int userId, int animeId){
        ListaAnimesKey id = new ListaAnimesKey();
        id.setIdAnime(animeId);
        id.setIdUsuario(userId);
        this.id = id;
    }

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

    public AnimeModel getAnimeModel() {
        return animeModel;
    }

    public void setAnimeModel(AnimeModel animeModel) {
        this.animeModel = animeModel;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}