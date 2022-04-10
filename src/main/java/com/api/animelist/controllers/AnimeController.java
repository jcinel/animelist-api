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

    // Construtor
    public AnimeController(AnimeService animeService) { this.animeService = animeService; }

    // Método responsável por inserir um novo anime
    @PostMapping
    public ResponseEntity<Object> saveAnime(@RequestBody @Valid AnimeDto animeDto){
        // Confere se esse anime já existe na lista
        boolean animeModelExists = animeService.existsByNome(animeDto.getNome());
        // Caso o anime já exista há um conflito
        if (animeModelExists){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Anime já existente");
        }
        // Caso contrário o anime é inserido normalmente
        var animeModel = new AnimeModel();
        BeanUtils.copyProperties(animeDto, animeModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(animeService.save(animeModel));
    }

    // Método responsável por exibir todos o animes da lista
    @GetMapping
    public ResponseEntity<Page<AnimeModel>> getAllAnimes(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(animeService.findAll(pageable));
    }

    // Método responsável por exibir um único anime de acordo com o id que foi passado
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneAnime(@PathVariable(value = "id") int id){
        Optional<AnimeModel> animeModelOptional = animeService.findById(id);
        if (!animeModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anime não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(animeModelOptional.get());
    }

    // Método responsável por atualizar um ou mais campos de um anime
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAnime(@PathVariable(value = "id") int id, @RequestBody @Valid AnimeDto animeDto){
        Optional<AnimeModel> animeModelOptional = animeService.findById(id);
        if (!animeModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anime não encontrado");
        }
        var animeModel = animeModelOptional.get();
        animeModel.setNome(animeDto.getNome());
        animeModel.setAutor(animeDto.getAutor());
        animeModel.setSinopse(animeDto.getSinopse());
        return ResponseEntity.status(HttpStatus.OK).body(animeService.save(animeModel));
    }

    // Método responsável por deletar um anime da lista
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
