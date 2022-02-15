package com.api.animelist.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ListaMangasKey implements Serializable {
    @Column(name = "ID_Manga")
    private int IdManga;
    @Column(name = "ID_Usuario")
    private int IdUsuario;

    public int getIdManga() {
        return IdManga;
    }

    public void setIdManga(int idManga) {
        IdManga = idManga;
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
        ListaMangasKey that = (ListaMangasKey) o;
        return IdManga == that.IdManga && IdUsuario == that.IdUsuario;
    }

    @Override
    public int hashCode() {
        return Objects.hash(IdManga, IdUsuario);
    }
}
