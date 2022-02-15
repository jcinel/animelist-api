package com.api.animelist.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="ListaAnimes")
public class ListaMangasModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ListaMangasKey id;
    @Column
    private float nota;
    @Column(nullable = false, length = 50)
    private String status;
    @ManyToOne
    @MapsId("IdManga")
    @JoinColumn(name = "ID_Manga")
    private MangaModel manga;
    @ManyToOne
    @MapsId("IdUsuario")
    @JoinColumn(name = "ID_Usuario")
    private UserModel usuario;

    public ListaMangasKey getId() {
        return id;
    }

    public void setId(ListaMangasKey id) {
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

    public MangaModel getManga() {
        return manga;
    }

    public void setManga(MangaModel manga) {
        this.manga = manga;
    }

    public UserModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UserModel usuario) {
        this.usuario = usuario;
    }
}
