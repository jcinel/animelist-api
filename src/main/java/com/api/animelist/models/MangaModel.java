package com.api.animelist.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Mangas")
public class MangaModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false, length = 50)
    private String nome;
    @Column(nullable = false, length = 50)
    private String autor;
}

