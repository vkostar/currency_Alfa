package com.example.currencyalfa.currency_alfa.controllers;


import com.example.currencyalfa.currency_alfa.client.GifClient;
import com.example.currencyalfa.currency_alfa.client.RatesClient;

import com.example.currencyalfa.currency_alfa.models.Rates;
import com.example.currencyalfa.currency_alfa.services.ServiceMain;
import com.example.currencyalfa.currency_alfa.util.NotFoundProperGif;
import com.example.currencyalfa.currency_alfa.util.UtilForCalculate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
        Map<String, Double> mapa = serviceMain.getCurrentRates();
        Map<Integer, String> list = new HashMap<>();
//        list.put("first", 1);
//        list.put("second", 2);
        String current = "";
        model.addAttribute("currentAll", mapa);
        model.addAttribute("current", current);
        return "image";
    }


    @GetMapping("/{currency}")
    public Object getRates(@PathVariable("currency") String currency, @ModelAttribute("current") String currency2,
                           Model model) throws NotFoundProperGif {
//        Map<String, Double> currentRates = serviceMain.getCurrentRates();
        String UrlName = util.calculateProperWordForRequest(currency);
        String ULR = serviceMain.getUrl(UrlName);
        model.addAttribute("src", ULR);
        return "gifka";
    }

//    @GetMapping("/historyRates")
//    public Object getHistoryRates() {
//
//        Rates rates = serviceMain.getHistoricRates("");
//
//        return rates.getRates();
//    }


}

