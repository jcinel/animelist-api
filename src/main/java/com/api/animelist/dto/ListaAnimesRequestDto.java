package com.api.animelist.dto;

import com.api.animelist.models.ListaAnimesModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ListaAnimesRequestDto {

    @NotNull
    private int userId;
    @NotNull
    private int animeId;
    private float nota;
    @NotBlank
    private String status;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAnimeId() {
        return animeId;
    }

    public void setAnimeId(int animeId) {
        this.animeId = animeId;
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
}
