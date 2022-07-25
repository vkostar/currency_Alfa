package com.example.currencyalfa.currency_alfa.client;

import com.example.currencyalfa.currency_alfa.models.Rates;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "RatesClient", url = "${rates.url}")
public interface RatesClient {
    @GetMapping("/latest.json")
    Rates getRates(@RequestParam("app_id") String app_id);
    @GetMapping("/historical/{date}.json")
    Rates getHistorical(@PathVariable String date, @RequestParam("app_id") String appId);
}

