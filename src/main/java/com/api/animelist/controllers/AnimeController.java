package com.api.animelist.controllers;

import com.api.animelist.dto.AnimeDto;
import com.api.animelist.models.AnimeModel;
import com.api.animelist.services.AnimeService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3000)
@RequestMapping("/api/animes")
public class AnimeController {

    final AnimeService animeService;

    public AnimeController(AnimeService animeService) { this.animeService = animeService; }

    @PostMapping
    public ResponseEntity<Object> saveAnime(@RequestBody @Valid AnimeDto animeDto){
        var animeModel = new AnimeModel();
        BeanUtils.copyProperties(animeDto, animeModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(animeService.save(animeModel));
    }
}
