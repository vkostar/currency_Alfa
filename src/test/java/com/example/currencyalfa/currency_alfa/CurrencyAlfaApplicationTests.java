package com.example.currencyalfa.currency_alfa;

import com.example.currencyalfa.currency_alfa.models.Rates;
import com.example.currencyalfa.currency_alfa.services.ServiceMain;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;


@SpringBootTest
class CurrencyAlfaApplicationTests {

    @MockBean
    private com.example.currencyalfa.currency_alfa.client.RatesClient RatesClient;

    @Autowired
    ServiceMain serviceMain;

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
        Date yesterdayDate = new Date(currentTime - 24 * 60 * 60 * 1000);
        String yesterdayFormat = sdf.format(yesterdayDate);

        Mockito.when(RatesClient.getRates(Mockito.anyString()))
                .thenReturn(todayRates);
        Mockito.when(RatesClient.getHistorical(Mockito.eq(yesterdayFormat), Mockito.anyString()))
                .thenReturn(yesterdayRates);
        Assert.assertTrue(serviceMain.getCurrentRates().get("CURR1")
                > serviceMain.getHistoricRates(yesterdayFormat).get("CURR1"));

//        Assert.assertTrue(
//                RatesClient.getRates("test").getRates().get("CURR1")  >
//                        RatesClient.getHistorical(yesterdayFormat,"test").getRates().get("CURR1"));

        Assert.assertTrue(serviceMain.getCurrentRates().get("CURR2")
               < serviceMain.getHistoricRates(yesterdayFormat).get("CURR2"));

        Assert.assertTrue(Objects.equals(serviceMain.getCurrentRates().get("CURR3"), serviceMain.getHistoricRates(yesterdayFormat).get("CURR3")));

    }

}
