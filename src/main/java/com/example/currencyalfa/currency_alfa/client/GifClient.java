package com.example.currencyalfa.currency_alfa.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@FeignClient(name = "GifClient", url = "${client.url}")
public interface GifClient {

    @GetMapping()
    ResponseEntity<Map> getImage(
            @RequestParam("api_key") String apiKey,
            @RequestParam("tag") String tag);

}

