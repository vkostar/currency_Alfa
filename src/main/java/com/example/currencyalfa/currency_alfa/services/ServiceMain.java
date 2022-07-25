package com.example.currencyalfa.currency_alfa.services;

import com.example.currencyalfa.currency_alfa.client.GifClient;
import com.example.currencyalfa.currency_alfa.client.RatesClient;
import com.example.currencyalfa.currency_alfa.models.Rates;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ServiceMain {

    GifClient gifClient;
    ModelMapper modelMapper;
    RatesClient ratesClient;
    @Value("${rates_api_key}")
    String apiRatesKey;

    @Autowired
    public ServiceMain(GifClient gifClient, ModelMapper modelMapper, RatesClient ratesClient) {
        this.gifClient = gifClient;
        this.modelMapper = modelMapper;
        this.ratesClient = ratesClient;
    }


    public String getUrl(String tag) {
        Map gif = (Map) gifClient.getImage("x5ne24uuC6kFZLiAiQN2WLnH5ae0xBkI", tag).getBody().get("data");

        Map gif1 = (Map) gif.get("images");
        Map gif2 = (Map) gif1.get("original");
        return (String) gif2.get("url");

    }


    public Map<String, Double> getHistoricRates(String date) {
        Rates rates = ratesClient.getHistorical(date, apiRatesKey);
        return rates.getRates();
    }

    public Map<String, Double> getCurrentRates() {
        Rates rates = ratesClient.getRates(apiRatesKey);
        return rates.getRates();
    }
}
