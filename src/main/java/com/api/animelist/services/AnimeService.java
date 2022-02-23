package com.api.animelist.services;

import com.api.animelist.models.AnimeModel;
import com.api.animelist.repositories.AnimeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class AnimeService {

    final AnimeRepository animeRepository;

    public AnimeService(AnimeRepository animeRepository) { this.animeRepository = animeRepository; }

    public boolean existsByNome(String nome) {
        return animeRepository.existsByNome(nome);
    }

    @Transactional
    public Object save(AnimeModel animeModel) { return animeRepository.save(animeModel); }

    public Page<AnimeModel> findAll(Pageable pageable) {
        return animeRepository.findAll(pageable);
    }

    public Optional<AnimeModel> findById(int id) {
        return animeRepository.findById(id);
    }

    @Transactional
    public void delete(AnimeModel animeModel) { animeRepository.delete(animeModel); }
}
