package com.api.animelist.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="ListaAnimes")
public class ListaMangasModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column
    private float nota;
    @Column(nullable = false, length = 50)
    private String status;

}
