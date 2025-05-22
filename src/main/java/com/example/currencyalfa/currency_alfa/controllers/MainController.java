package com.example.currencyalfa.currency_alfa.controllers;


import com.example.currencyalfa.currency_alfa.client.GifClient;
import com.example.currencyalfa.currency_alfa.client.RatesClient;
import com.example.currencyalfa.currency_alfa.models.ViewObject;
import com.example.currencyalfa.currency_alfa.services.ServiceMain;
import com.example.currencyalfa.currency_alfa.util.UtilForCalculate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
public class MainController {

    ServiceMain serviceMain;
    RatesClient ratesClient;
    GifClient gifClient;
    ModelMapper modelMapper;
    UtilForCalculate util;


    public MainController(ServiceMain serviceMain, RatesClient ratesClient, GifClient gifClient, ModelMapper modelMapper, UtilForCalculate util) {
        this.serviceMain = serviceMain;
        this.ratesClient = ratesClient;
        this.gifClient = gifClient;
        this.modelMapper = modelMapper;
        this.util = util;
    }

    @GetMapping
    public String getListOfRates(Model model) {
        Map<String, Double> mapa = serviceMain.getCurrentRates();
        model.addAttribute("currentAll", mapa);
        return "image";
    }


    @PostMapping("/getGif")
    public Object getGif(@ModelAttribute("unit") ViewObject unit,
                         Model model) {
        String UrlName = util.calculateProperWordForRequest(unit.getCurrencyCode());
        String ULR = serviceMain.getUrl(UrlName);
        model.addAttribute("src", ULR);


        return "gifka";
    }


}

