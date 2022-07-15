package com.example.currencyalfa.currency_alfa.services;

import com.example.currencyalfa.currency_alfa.client.GifClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ServiceMain {

    GifClient gifClient;
    ModelMapper modelMapper;

    @Autowired
    public ServiceMain(GifClient gifClient, ModelMapper modelMapper) {
        this.gifClient = gifClient;
        this.modelMapper = modelMapper;
    }

    public String getUrl(String query) {
        Map gif = (Map) gifClient.getImage("x5ne24uuC6kFZLiAiQN2WLnH5ae0xBkI", query)
                .getBody().get("data");


        return (String) gif.get("embed_url");
    }

}
