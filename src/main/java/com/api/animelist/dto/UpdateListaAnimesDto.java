package com.api.animelist.dto;


import javax.validation.constraints.NotBlank;

public class UpdateListaAnimesDto {

    private float nota;
    @NotBlank
    private String status;

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
