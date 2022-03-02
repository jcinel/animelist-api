package com.api.animelist.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ListaAnimesKey implements Serializable {

    @Column(name = "id_anime")
    private int IdAnime;
    @Column(name = "id_usuario")
    private int IdUsuario;

    public ListaAnimesKey(){}

    public ListaAnimesKey(int idUsuario, int idAnime){
        this.setIdUsuario(idUsuario);
        this.setIdAnime(idAnime);
    }

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
