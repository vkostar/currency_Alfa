package com.example.currencyalfa.currency_alfa.util;


import com.example.currencyalfa.currency_alfa.services.ServiceMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


@Component
public class UtilForCalculate {

    ServiceMain serviceMain;

    @Autowired
    public UtilForCalculate(ServiceMain serviceMain) {
        this.serviceMain = serviceMain;
    }

    public String getYesterdayString() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -1);
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(cal.getTime());
    }

    public String calculateProperWordForRequest(String nameOfCurrencies) {
        Map<String, Double> currentRates = serviceMain.getCurrentRates();
        Map<String, Double> historicRates = serviceMain.getHistoricRates(getYesterdayString());
        Double currentValue = currentRates.get(nameOfCurrencies);
        Double pastValue = historicRates.get(nameOfCurrencies);
        if (currentValue > pastValue) {
            return "rich";
        }
        if (currentValue < pastValue) {
            return "bankrupt";
        } else return "equal";

    }


}
