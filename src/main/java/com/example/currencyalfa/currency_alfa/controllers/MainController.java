package com.example.currencyalfa.currency_alfa.controllers;


import com.example.currencyalfa.currency_alfa.client.GifClient;
import com.example.currencyalfa.currency_alfa.client.RatesClient;

import com.example.currencyalfa.currency_alfa.services.ServiceMain;
import com.example.currencyalfa.currency_alfa.util.NotFoundProperGif;
import com.example.currencyalfa.currency_alfa.util.UtilForCalculate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@Controller
public class MainController {

    ServiceMain serviceMain;
    RatesClient ratesClient;
    GifClient gifClient;
    ModelMapper modelMapper;
    UtilForCalculate util;

    @Value("${gif_api_key}")
    String apiGifKey;


    public MainController(ServiceMain serviceMain, RatesClient ratesClient, GifClient gifClient, ModelMapper modelMapper, UtilForCalculate util) {
        this.serviceMain = serviceMain;
        this.ratesClient = ratesClient;
        this.gifClient = gifClient;
        this.modelMapper = modelMapper;
        this.util = util;
    }

    @GetMapping
    public String getGif(Model model) throws NotFoundProperGif {


        String UrlName = util.calculateProperWordForRequest("AUD");
        String ULR = serviceMain.getUrl(UrlName);
        model.addAttribute("src", ULR);
        return "image";
    }


    @GetMapping("/rates")
    public Object getRates() {
        Map rates = serviceMain.getCurrentRates();


        return rates;
    }

//    @GetMapping("/historyRates")
//    public Object getHistoryRates() {
//
//        Rates rates = serviceMain.getHistoricRates("");
//
//        return rates.getRates();
//    }


}

