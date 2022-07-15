package com.example.currencyalfa.currency_alfa;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class CurrencyAlfaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyAlfaApplication.class, args);
    }


    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
