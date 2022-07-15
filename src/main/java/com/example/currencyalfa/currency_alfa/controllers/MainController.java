package com.example.currencyalfa.currency_alfa.controllers;


import com.example.currencyalfa.currency_alfa.client.GifClient;
import com.example.currencyalfa.currency_alfa.models.Gif;
import com.example.currencyalfa.currency_alfa.services.ServiceMain;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MainController {

    ServiceMain serviceMain;

    GifClient gifClient;
    ModelMapper modelMapper;

    @Value("${gif_api_key}")
    String apiKey;

    public MainController(ServiceMain serviceMain, GifClient gifClient, ModelMapper modelMapper) {
        this.serviceMain = serviceMain;
        this.gifClient = gifClient;
        this.modelMapper = modelMapper;

    }

    @GetMapping
    public String Object() {
        return serviceMain.getUrl("house");
    }


}

