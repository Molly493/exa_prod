package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    /*@Autowired
    private ItemService itemService;

    @PostMapping
    public Item addItem(@RequestBody Item item){
        logger.info("Llamando a ItemService para guardar el item: {}", item);
        return itemService.guardar(producto);
    }*/
    @GetMapping("/hello")
    public String hello() {
        logger.info("Servicio get Hello World");
        return "Hello, World!";
    }

    @GetMapping("/service")
    public String service() {
        logger.info("Servicio prueba get");
        return "Service!";
    }
}
