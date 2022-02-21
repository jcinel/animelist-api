package com.api.animelist.controller;

import com.api.animelist.controllers.AnimeController;
import com.api.animelist.models.AnimeModel;
import com.api.animelist.services.AnimeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class AnimeControllerTest {

    @Autowired
    private AnimeService animeService;

    private AnimeController animeController;

    @Test
    public void getOneTest(){
        animeController = new AnimeController(animeService);
        ResponseEntity<Object> response = animeController.getOneAnime(5);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        response = animeController.getOneAnime(2);
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


}
