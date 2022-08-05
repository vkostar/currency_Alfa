package com.example.currencyalfa.currency_alfa;

import com.example.currencyalfa.currency_alfa.client.RatesClient;
import com.example.currencyalfa.currency_alfa.models.Rates;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.text.SimpleDateFormat;
import java.util.HashMap;

@SpringBootTest
class CurrencyAlfaApplicationTests {

    @MockBean
    private com.example.currencyalfa.currency_alfa.client.RatesClient RatesClient;



    @Test
    void testExchangeRates() {


        Rates todayRates = new Rates();
        todayRates.setRates(new HashMap<>());
        todayRates.getRates().put("CURR1", 100.0);
        todayRates.getRates().put("CURR2", 100.0);
        todayRates.getRates().put("CURR3", 100.0);

        Rates yesterdayRates = new Rates();
        yesterdayRates.setRates(new HashMap<>());
        yesterdayRates.getRates().put("CURR1", 90.0);
        yesterdayRates.getRates().put("CURR2", 110.0);
        yesterdayRates.getRates().put("CURR3", 100.0);

        //Определим поведение заглушки
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long currentTime = System.currentTimeMillis();
        Date todayDate = new Date(currentTime);
        Date yesterdayDate = new Date(currentTime-24*60*60*1000);
        String todayFormat = sdf.format(todayDate);
        String yesterdayFormat = sdf.format(yesterdayDate);

        Mockito.when(exchangeRatesClient.getExchangeRates(Mockito.eq(todayFormat), Mockito.anyString()))
                .thenReturn(todayRates);
        Mockito.when(exchangeRatesClient.getExchangeRates(Mockito.eq(yesterdayFormat), Mockito.anyString()))
                .thenReturn(yesterdayRates);

        Assert.assertTrue(
                exchangeRatesService.getRates(todayDate, "CURR1")>
                        exchangeRatesService.getRates(yesterdayDate, "CURR1"));
        Assert.assertTrue(
                exchangeRatesService.getRates(todayDate, "CURR2")<
                        exchangeRatesService.getRates(yesterdayDate, "CURR2"));
        Assert.assertTrue(
                exchangeRatesService.getRates(todayDate, "CURR3")==
                        exchangeRatesService.getRates(yesterdayDate, "CURR3"));

    }

}
