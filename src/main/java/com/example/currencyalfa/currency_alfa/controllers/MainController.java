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


    @GetMapping("/")
    public String getListOfRates(Model model) {
        // Получаем все курсы
        Map<String, Double> mapa = serviceMain.getCurrentRates();
        model.addAttribute("currentAll", mapa);

        // Пустой объект для биндинга формы
        model.addAttribute("unit", new ViewObject());

        // Если нужно показать список кодов валют в форме
        model.addAttribute("codes", mapa.keySet());
        return "image";
    }

    @PostMapping("/getGif")
    public String getGif(@ModelAttribute("unit") ViewObject unit,
                         Model model) {
        // Получаем слово для запроса гифки
        String urlName = util.calculateProperWordForRequest(unit.getCurrencyCode());
        String gifUrl  = serviceMain.getUrl(urlName);

        model.addAttribute("src", gifUrl);
        return "gifka";
    }



}

