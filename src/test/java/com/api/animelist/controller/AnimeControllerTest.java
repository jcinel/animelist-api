package com.api.animelist.controller;

import com.api.animelist.controllers.AnimeController;
import com.api.animelist.dto.AnimeDto;
import com.api.animelist.models.AnimeModel;
import com.api.animelist.services.AnimeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
// Testes na classe AnimeController
public class AnimeControllerTest {

    @Mock
    private AnimeService animeService;

    private AnimeController animeController;

    // Inserindo um novo anime
    @Test
    public void postOneAnimeTest(){
        animeController = new AnimeController(animeService);
        AnimeModel animeModel = new AnimeModel();
        animeModel.setNome("Demon Slayer");
        AnimeDto animeDto = new AnimeDto();
        animeDto.setNome("Naruto");
        animeDto.setAutor("Kishimoto");

        when(animeService.existsByNome("Naruto")).thenReturn(true);

        ResponseEntity<Object> response = animeController.saveAnime(animeDto);
        Assert.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

        animeDto.setNome("Demon Slayer");
        animeDto.setAutor("Koyoharu");

        when(animeService.save(animeModel)).thenReturn(animeModel);

        response = animeController.saveAnime(animeDto);
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    // Retorna todos os animes da lista geral
    @Test
    public void getAllAnimes(){
        animeController = new AnimeController(animeService);

        var pageable = PageRequest.of(0, 20);
        List<AnimeModel> animelist = Arrays.asList(new AnimeModel(), new AnimeModel());

        var page = new PageImpl<AnimeModel>(animelist, pageable, animelist.size());
        when(animeService.findAll(Mockito.any(PageRequest.class))).thenReturn(page);

        ResponseEntity<Page<AnimeModel>> response = animeController.getAllAnimes(pageable);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // Retorna um anime específico de acordo com o id que foi passado
    @Test
    public void getOneAnimeTest(){
        animeController = new AnimeController(animeService);

        when(animeService.findById(5)).thenReturn(Optional.of(new AnimeModel()));

        ResponseEntity<Object> response = animeController.getOneAnime(5);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        when(animeService.findById(2)).thenReturn(Optional.empty());

        response = animeController.getOneAnime(2);
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Atualiza um ou mais campos em um anime específico
    @Test
    public void updateOneAnimeTest(){
        animeController = new AnimeController(animeService);
        AnimeModel animeModel = new AnimeModel();
        animeModel.setNome("Demon Slayer");
        animeModel.setAutor("Koyoharu");
        AnimeDto animeDto = new AnimeDto();
        animeDto.setNome("Kimetsu no Yaiba");
        animeDto.setAutor("Koyoharu Gotoge");

        when(animeService.findById(8)).thenReturn(Optional.of(animeModel));

        ResponseEntity<Object> response = animeController.updateAnime(8, animeDto);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        when(animeService.findById(2)).thenReturn(Optional.empty());

        response = animeController.updateAnime(2, animeDto);
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Deleta um anime específico
    @Test
    public void deleteOneAnimeTest(){
        animeController = new AnimeController(animeService);

        when(animeService.findById(7)).thenReturn(Optional.of(new AnimeModel()));

        ResponseEntity<Object> response = animeController.deleteAnime(7);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        when(animeService.findById(2)).thenReturn(Optional.empty());

        response = animeController.deleteAnime(1);
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
