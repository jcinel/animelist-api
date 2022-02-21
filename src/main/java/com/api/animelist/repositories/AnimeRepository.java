package com.api.animelist.repositories;

import com.api.animelist.models.AnimeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimeRepository extends JpaRepository<AnimeModel, Integer> {
    boolean findByNome(String nome);
}
