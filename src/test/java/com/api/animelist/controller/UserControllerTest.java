package com.api.animelist.controller;

import com.api.animelist.controllers.UserController;
import com.api.animelist.services.AnimeService;
import com.api.animelist.services.ListaAnimesService;
import com.api.animelist.services.UserService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class UserControllerTest {

    @Autowired
    private UserService userService;
    @Autowired
    private AnimeService animeService;
    @Autowired
    private ListaAnimesService listaAnimesService;

    private UserController userController;
}
