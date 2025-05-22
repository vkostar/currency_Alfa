package com.example.currencyalfa.currency_alfa.services;

import com.example.currencyalfa.currency_alfa.client.GifClient;
import com.example.currencyalfa.currency_alfa.client.RatesClient;
import com.example.currencyalfa.currency_alfa.exceptions.GiphyApiException;
import com.example.currencyalfa.currency_alfa.models.GiphyResponse;
import com.example.currencyalfa.currency_alfa.models.Rates;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service

public class ServiceMain {

    GifClient gifClient;
    ModelMapper modelMapper;
    RatesClient ratesClient;
    @Value("${rates_api_key}")
    String apiRatesKey;

    @Value("${giphy.api.key}")
    private String giphyApiKey;

    @Autowired
    public ServiceMain(GifClient gifClient, ModelMapper modelMapper, RatesClient ratesClient) {
        this.gifClient = gifClient;
        this.modelMapper = modelMapper;
        this.ratesClient = ratesClient;
    }


    public String getUrl(String tag) {
        ResponseEntity<GiphyResponse> responseEntity = gifClient.getImage(giphyApiKey, tag);

        GiphyResponse giphyResponse = Optional.ofNullable(responseEntity)
                .map(ResponseEntity::getBody)
                .orElseThrow(() -> new GiphyApiException("Giphy API response or body is null"));

        String url = Optional.ofNullable(giphyResponse)
                .map(GiphyResponse::getData)
                .map(data -> data.getImages())
                .map(images -> images.getOriginal())
                .map(original -> original.getUrl())
                .orElseThrow(() -> new GiphyApiException("Invalid Giphy API response structure - URL not found"));

        if (url.isEmpty()) {
            throw new GiphyApiException("Giphy API response structure is valid, but URL is empty");
        }
        return url;
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
