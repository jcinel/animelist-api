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

}