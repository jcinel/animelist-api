package com.api.animelist.controllers;

import com.api.animelist.dto.AnimeDto;
import com.api.animelist.models.AnimeModel;
import com.api.animelist.services.AnimeService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3000)
@RequestMapping("/api/animes")
public class AnimeController {

    final AnimeService animeService;

    public AnimeController(AnimeService animeService) { this.animeService = animeService; }

    @PostMapping
    public ResponseEntity<Object> saveAnime(@RequestBody @Valid AnimeDto animeDto){
        boolean animeModelExists = animeService.existsByNome(animeDto.getNome());
        if (animeModelExists){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Anime já existente");
        }
        var animeModel = new AnimeModel();
        BeanUtils.copyProperties(animeDto, animeModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(animeService.save(animeModel));
    }

    @GetMapping
    public ResponseEntity<Page<AnimeModel>> getAllAnimes(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(animeService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneAnime(@PathVariable(value = "id") int id){
        Optional<AnimeModel> animeModelOptional = animeService.findById(id);
        if (!animeModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anime não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(animeModelOptional.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAnime(@PathVariable(value = "id") int id, @RequestBody @Valid AnimeDto animeDto){
        Optional<AnimeModel> animeModelOptional = animeService.findById(id);
        if (!animeModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anime não encontrado");
        }
        var animeModel = animeModelOptional.get();
        animeModel.setNome(animeDto.getNome());
        animeModel.setAutor(animeDto.getAutor());
        return ResponseEntity.status(HttpStatus.OK).body(animeService.save(animeModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAnime(@PathVariable(value = "id") int id){
        Optional<AnimeModel> animeModelOptional = animeService.findById((id));
        if (!animeModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anime não encontrado");
        }
        animeService.delete(animeModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Anime deletado com sucesso");
    }
}
