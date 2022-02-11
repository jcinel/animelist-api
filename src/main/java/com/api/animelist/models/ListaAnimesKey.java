package com.api.animelist.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ListaAnimesKey implements Serializable {

    @Column(name = "ID_Anime")
    private int IdAnime;
    @Column(name = "ID_Usuario")
    private int IdUsuario;

    public int getIdAnime() {
        return IdAnime;
    }

    public void setIdAnime(int idAnime) {
        IdAnime = idAnime;
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        IdUsuario = idUsuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListaAnimesKey that = (ListaAnimesKey) o;
        return IdAnime == that.IdAnime && IdUsuario == that.IdUsuario;
    }

    @Override
    public int hashCode() {
        return Objects.hash(IdAnime, IdUsuario);
    }
}
