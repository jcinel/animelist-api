package com.api.animelist.services;

import com.api.animelist.models.AnimeModel;
import com.api.animelist.repositories.AnimeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AnimeService {

    final AnimeRepository animeRepository;

    public AnimeService(AnimeRepository animeRepository) { this.animeRepository = animeRepository; }

    @Transactional
    public Object save(AnimeModel animeModel) { return animeRepository.save(animeModel); }
}
