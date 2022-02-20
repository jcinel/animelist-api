package com.api.animelist.controllers;

import com.api.animelist.dto.ListaAnimesRequestDto;
import com.api.animelist.dto.ListaAnimesResponseDto;
import com.api.animelist.dto.UserDto;
import com.api.animelist.models.AnimeModel;
import com.api.animelist.models.ListaAnimesKey;
import com.api.animelist.models.ListaAnimesModel;
import com.api.animelist.models.UserModel;
import com.api.animelist.services.AnimeService;
import com.api.animelist.services.ListaAnimesService;
import com.api.animelist.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3000)
@RequestMapping("/api/users")
public class UserController {

    final UserService userService;

    final AnimeService animeService;

    final ListaAnimesService listaAnimesService;

    public UserController(UserService userService, AnimeService animeService, ListaAnimesService listaAnimesService) {
        this.userService = userService;
        this.animeService = animeService;
        this.listaAnimesService = listaAnimesService;
    }

    @PostMapping
    public ResponseEntity<Object> saveUser(@RequestBody @Valid UserDto userDto) {
        if (userService.existsByEmail(userDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Este e-mail está indisponível");
        }

        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel));
    }

    @GetMapping
    public ResponseEntity<Page<UserModel>> getAllUsers(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "id") int id) {
        Optional<UserModel> userModelOptional = userService.findById(id);
        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") int id, @RequestBody @Valid UserDto userDto) {
        Optional<UserModel> userModelOptional = userService.findById(id);
        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        var userModel = userModelOptional.get();
        userModel.setNome(userDto.getNome());
        userModel.setDataNasc(userDto.getDataNasc());
        userModel.setEmail(userDto.getEmail());
        userModel.setSenha(userDto.getSenha());
        return ResponseEntity.status(HttpStatus.OK).body(userService.save(userModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") int id) {
        Optional<UserModel> userModelOptional = userService.findById(id);
        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        userService.delete(userModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso");
    }

    @PostMapping("/{id}/animes")
    public ResponseEntity<Object> createListaAnimes
            (@PathVariable(value = "id") int id, @RequestBody @Valid ListaAnimesRequestDto requestDto) {
        Optional<UserModel> userModelOptional = userService.findById(id);
        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        Optional<AnimeModel> animeModelOptional = animeService.findById(requestDto.getAnimeId());
        if (!animeModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anime não encontrado");
        }
        ListaAnimesModel listaAnimesModel = new ListaAnimesModel(requestDto.getUserId(), requestDto.getAnimeId());

        listaAnimesModel.setNota(requestDto.getNota());
        listaAnimesModel.setStatus(requestDto.getStatus());
        listaAnimesModel.setAnimeModel(animeModelOptional.get());
        listaAnimesModel.setUserModel(userModelOptional.get());
        listaAnimesService.save(listaAnimesModel);
        var responseDto = ListaAnimesResponseDto.build(listaAnimesModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{id}/animes")
    public ResponseEntity<Object> getListaAnimes(@PathVariable(value = "id") int id) {
        Optional<UserModel> userModelOptional = userService.findById(id);
        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        List<ListaAnimesModel> listaAnimes = listaAnimesService.findAllByUser(id);
        List<ListaAnimesResponseDto> animesUsuario = new ArrayList<>();
        for (var model : listaAnimes) {
            var responseDto = ListaAnimesResponseDto.build(model);
            animesUsuario.add(responseDto);
        }
        return ResponseEntity.status(HttpStatus.OK).body(animesUsuario);
    }

    @PutMapping("/{id}/animes/{idanime}")
    public ResponseEntity<Object> updateListaAnimes
            (@PathVariable(value = "id") int id,
             @PathVariable(value = "idanime") int idAnime,
             @RequestBody @Valid ListaAnimesRequestDto requestDto) {
        Optional<UserModel> userModelOptional = userService.findById(id);
        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        Optional<AnimeModel> animeModelOptional = animeService.findById(requestDto.getAnimeId());
        if (!animeModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anime não encontrado");
        }
        ListaAnimesKey listaAnimesKey = new ListaAnimesKey(id, idAnime);
        Optional<ListaAnimesModel> listaAnimesModelOptional = listaAnimesService.findById(listaAnimesKey);
        if (!listaAnimesModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anime não encontrado na lista");
        }

        var listaAnimesModel = listaAnimesModelOptional.get();
        listaAnimesModel.setNota(requestDto.getNota());
        listaAnimesModel.setStatus(requestDto.getStatus());
        listaAnimesService.save(listaAnimesModel);
        var responseDto = ListaAnimesResponseDto.build(listaAnimesModel);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @DeleteMapping("/{id}/animes/{idanime}")
    public ResponseEntity<Object> deleteListaAnimes
            (@PathVariable(value = "id") int id,
             @PathVariable(value = "idanime") int idAnime) {
        Optional<UserModel> userModelOptional = userService.findById(id);
        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        Optional<AnimeModel> animeModelOptional = animeService.findById(idAnime);
        if (!animeModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anime não encontrado");
        }
        ListaAnimesKey listaAnimesKey = new ListaAnimesKey(id, idAnime);
        Optional<ListaAnimesModel> listaAnimesModelOptional = listaAnimesService.findById(listaAnimesKey);
        if (!listaAnimesModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anime não encontrado na lista");
        }

        var listaAnimesModel = listaAnimesModelOptional.get();
        listaAnimesService.delete(listaAnimesModel);
        return ResponseEntity.status(HttpStatus.OK).body("Anime deletado da lista com sucesso");
    }
}