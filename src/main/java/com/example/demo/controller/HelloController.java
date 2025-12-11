package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<?> hello(@RequestParam(required = false) Integer number) {
        logger.info("Servicio get Hello World con parámetro number: {}", number);
        if (number != null && number == 1) {
            return ResponseEntity.ok(new ResponseMessage("hello word", "okresponse"));
        } else {
            return ResponseEntity.badRequest().body(new ResponseMessage("badresponse", "error"));
        }
    }

    @GetMapping("/service")
    public String service() {
        logger.info("Servicio prueba get");
        return "Service!";
    }

    // Clase interna para estructurar la respuesta JSON
    public static class ResponseMessage {
        private String message;
        private String response;
        public ResponseMessage(String message, String response) {
            this.message = message;
            this.response = response;
        }
        // Getters (necesarios para JSON serialization)
        public String getMessage() {
            return message;
        }
        public String getResponse() {
            return response;
        }
    }
}
