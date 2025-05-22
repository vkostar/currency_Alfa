package com.example.currencyalfa.currency_alfa.util;

import com.example.currencyalfa.currency_alfa.exceptions.CurrencyNotFoundException;
import com.example.currencyalfa.currency_alfa.services.ServiceMain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UtilForCalculateTest {

    @Mock
    private ServiceMain serviceMain;

    @InjectMocks
    private UtilForCalculate utilForCalculate;

    private final String TEST_CURRENCY = "TEST";
    private final String YESTERDAY_DATE_STRING = "mocked-date"; // Exact date doesn't matter due to mocking

    @BeforeEach
    void setUp() {
        // Mock getYesterdayString() to prevent actual date calculation and to ensure consistency
        // Since getYesterdayString is a public method of the class under test,
        // we can't directly mock it using @Mock or @Spy on UtilForCalculate itself
        // without further refactoring of UtilForCalculate (e.g. making getYesterdayString protected and spying or
        // extracting date logic to a separate mockable service).
        // For this test, we'll assume getYesterdayString() works as expected and returns a valid string
        // as its direct output doesn't influence the logic being tested (comparison of rates),
        // only the argument passed to getHistoricRates.
        // If UtilForCalculate was spied, we could do: doReturn(YESTERDAY_DATE_STRING).when(utilForCalculate).getYesterdayString();
        // However, utilForCalculate is instantiated with @InjectMocks, so we can't easily spy on it here without manual setup.
    }

    @Test
    void whenCurrentRateHigher_thenReturnsRich() {
        Map<String, Double> currentRates = new HashMap<>();
        currentRates.put(TEST_CURRENCY, 100.0);
        Map<String, Double> historicRates = new HashMap<>();
        historicRates.put(TEST_CURRENCY, 90.0);

        when(serviceMain.getCurrentRates()).thenReturn(currentRates);
        when(serviceMain.getHistoricRates(utilForCalculate.getYesterdayString())).thenReturn(historicRates);
        // If getYesterdayString() was complex, we'd mock it like:
        // when(serviceMain.getHistoricRates(YESTERDAY_DATE_STRING)).thenReturn(historicRates);


        String result = utilForCalculate.calculateProperWordForRequest(TEST_CURRENCY);
        assertEquals("rich", result);
    }

    @Test
    void whenCurrentRateLower_thenReturnsBankrupt() {
        Map<String, Double> currentRates = new HashMap<>();
        currentRates.put(TEST_CURRENCY, 90.0);
        Map<String, Double> historicRates = new HashMap<>();
        historicRates.put(TEST_CURRENCY, 100.0);

        when(serviceMain.getCurrentRates()).thenReturn(currentRates);
        when(serviceMain.getHistoricRates(utilForCalculate.getYesterdayString())).thenReturn(historicRates);

        String result = utilForCalculate.calculateProperWordForRequest(TEST_CURRENCY);
        assertEquals("bankrupt", result);
    }

    @Test
    void whenCurrentRateEqual_thenReturnsEqual() {
        Map<String, Double> currentRates = new HashMap<>();
        currentRates.put(TEST_CURRENCY, 100.0);
        Map<String, Double> historicRates = new HashMap<>();
        historicRates.put(TEST_CURRENCY, 100.0);

        when(serviceMain.getCurrentRates()).thenReturn(currentRates);
        when(serviceMain.getHistoricRates(utilForCalculate.getYesterdayString())).thenReturn(historicRates);

        String result = utilForCalculate.calculateProperWordForRequest(TEST_CURRENCY);
        assertEquals("equal", result);
    }

    @Test
    void whenCurrentRateMissing_thenThrowsCurrencyNotFoundException() {
        Map<String, Double> currentRates = new HashMap<>(); // TEST_CURRENCY is missing
        Map<String, Double> historicRates = new HashMap<>();
        historicRates.put(TEST_CURRENCY, 100.0);

        when(serviceMain.getCurrentRates()).thenReturn(currentRates);
        when(serviceMain.getHistoricRates(utilForCalculate.getYesterdayString())).thenReturn(historicRates);

        Exception exception = assertThrows(CurrencyNotFoundException.class, () -> {
            utilForCalculate.calculateProperWordForRequest(TEST_CURRENCY);
        });
        assertEquals("Currency code '" + TEST_CURRENCY + "' not found or historical data unavailable.", exception.getMessage());
    }
    
    @Test
    void whenCurrentRateNull_thenThrowsCurrencyNotFoundException() {
        Map<String, Double> currentRates = new HashMap<>();
        currentRates.put(TEST_CURRENCY, null);
        Map<String, Double> historicRates = new HashMap<>();
        historicRates.put(TEST_CURRENCY, 100.0);

        when(serviceMain.getCurrentRates()).thenReturn(currentRates);
        when(serviceMain.getHistoricRates(utilForCalculate.getYesterdayString())).thenReturn(historicRates);

        Exception exception = assertThrows(CurrencyNotFoundException.class, () -> {
            utilForCalculate.calculateProperWordForRequest(TEST_CURRENCY);
        });
        assertEquals("Currency code '" + TEST_CURRENCY + "' not found or historical data unavailable.", exception.getMessage());
    }


    @Test
    void whenHistoricRateMissing_thenThrowsCurrencyNotFoundException() {
        Map<String, Double> currentRates = new HashMap<>();
        currentRates.put(TEST_CURRENCY, 100.0);
        Map<String, Double> historicRates = new HashMap<>(); // TEST_CURRENCY is missing

        when(serviceMain.getCurrentRates()).thenReturn(currentRates);
        when(serviceMain.getHistoricRates(utilForCalculate.getYesterdayString())).thenReturn(historicRates);

        Exception exception = assertThrows(CurrencyNotFoundException.class, () -> {
            utilForCalculate.calculateProperWordForRequest(TEST_CURRENCY);
        });
        assertEquals("Currency code '" + TEST_CURRENCY + "' not found or historical data unavailable.", exception.getMessage());
    }
    
    @Test
    void whenHistoricRateNull_thenThrowsCurrencyNotFoundException() {
        Map<String, Double> currentRates = new HashMap<>();
        currentRates.put(TEST_CURRENCY, 100.0);
        Map<String, Double> historicRates = new HashMap<>();
        historicRates.put(TEST_CURRENCY, null);

        when(serviceMain.getCurrentRates()).thenReturn(currentRates);
        when(serviceMain.getHistoricRates(utilForCalculate.getYesterdayString())).thenReturn(historicRates);

        Exception exception = assertThrows(CurrencyNotFoundException.class, () -> {
            utilForCalculate.calculateProperWordForRequest(TEST_CURRENCY);
        });
        assertEquals("Currency code '" + TEST_CURRENCY + "' not found or historical data unavailable.", exception.getMessage());
    }

    @Test
    void whenBothRatesMissing_thenThrowsCurrencyNotFoundException() {
        Map<String, Double> currentRates = new HashMap<>();
        Map<String, Double> historicRates = new HashMap<>();

        when(serviceMain.getCurrentRates()).thenReturn(currentRates);
        when(serviceMain.getHistoricRates(utilForCalculate.getYesterdayString())).thenReturn(historicRates);

        Exception exception = assertThrows(CurrencyNotFoundException.class, () -> {
            utilForCalculate.calculateProperWordForRequest(TEST_CURRENCY);
        });
        assertEquals("Currency code '" + TEST_CURRENCY + "' not found or historical data unavailable.", exception.getMessage());
    }
}
