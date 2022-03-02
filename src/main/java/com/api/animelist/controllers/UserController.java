package com.api.animelist.controllers;

import com.api.animelist.dto.CreateListaAnimesDto;
import com.api.animelist.dto.ListaAnimesResponseDto;
import com.api.animelist.dto.UpdateListaAnimesDto;
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

    // Construtor
    public UserController(UserService userService, AnimeService animeService, ListaAnimesService listaAnimesService) {
        this.userService = userService;
        this.animeService = animeService;
        this.listaAnimesService = listaAnimesService;
    }

    // Método responsável por inserir um usuário no sistema
    @PostMapping
    public ResponseEntity<Object> saveUser(@RequestBody @Valid UserDto userDto) {
        // Caso o e-mail já esteja sendo utilizado retorna um conflito
        if (userService.existsByEmail(userDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Este e-mail está indisponível");
        }

        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel));
    }

    // Método responsável por listar todos os usuários do sistema
    @GetMapping
    public ResponseEntity<Page<UserModel>> getAllUsers(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll(pageable));
    }

    // Método responsável por exibir um usuário de acordo com o id passado
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "id") int id) {
        Optional<UserModel> userModelOptional = userService.findById(id);
        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
    }

    // Método responsável por atualizar um ou mais campos de um usuário
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

    // Método responsável por deletar um usuário do sistema
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") int id) {
        Optional<UserModel> userModelOptional = userService.findById(id);
        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        userService.delete(userModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso");
    }

    // Método responsável por inserir um novo anime na lista de animes do usuário
    @PostMapping("/{id}/animes")
    public ResponseEntity<Object> createListaAnimes
            (@PathVariable(value = "id") int id, @RequestBody @Valid CreateListaAnimesDto requestDto) {
        Optional<UserModel> userModelOptional = userService.findById(id);
        // Confere se o usuário existe
        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        // Confere se o anime existe
        Optional<AnimeModel> animeModelOptional = animeService.findById(requestDto.getAnimeId());
        if (!animeModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anime não encontrado");
        }

        ListaAnimesModel listaAnimesModel = new ListaAnimesModel(id, requestDto.getAnimeId());

        ListaAnimesKey listaAnimesKey = new ListaAnimesKey(id, requestDto.getAnimeId());
        Optional<ListaAnimesModel> listaAnimesModelOptional = listaAnimesService.findById(listaAnimesKey);
        if (listaAnimesModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Anime já existente na lista");
        }

        listaAnimesModel.setNota(requestDto.getNota());
        listaAnimesModel.setStatus(requestDto.getStatus());
        listaAnimesModel.setAnimeModel(animeModelOptional.get());
        listaAnimesModel.setUserModel(userModelOptional.get());
        listaAnimesService.save(listaAnimesModel);
        var responseDto = ListaAnimesResponseDto.build(listaAnimesModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // Método responsável por exibir a lista de animes do usuário
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

    // Método responsável por atualizar um anime na lista de animes do usuário
    @PutMapping("/{id}/animes/{idanime}")
    public ResponseEntity<Object> updateListaAnimes
            (@PathVariable(value = "id") int id,
             @PathVariable(value = "idanime") int idAnime,
             @RequestBody @Valid UpdateListaAnimesDto requestDto) {
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
        listaAnimesModel.setNota(requestDto.getNota());
        listaAnimesModel.setStatus(requestDto.getStatus());
        listaAnimesService.save(listaAnimesModel);
        var responseDto = ListaAnimesResponseDto.build(listaAnimesModel);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // Método responsável por deletar um anime da lista de animes do usuário
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