package com.api.animelist.repositories;

import com.api.animelist.models.ListaAnimesKey;
import com.api.animelist.models.ListaAnimesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListaAnimesRepository extends JpaRepository<ListaAnimesModel, ListaAnimesKey> {
}
