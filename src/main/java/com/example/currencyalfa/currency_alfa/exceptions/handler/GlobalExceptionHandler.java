package com.example.currencyalfa.currency_alfa.exceptions.handler;

import com.example.currencyalfa.currency_alfa.exceptions.CurrencyNotFoundException;
import com.example.currencyalfa.currency_alfa.exceptions.GiphyApiException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CurrencyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleCurrencyNotFoundException(CurrencyNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error_view";
    }

    @ExceptionHandler(GiphyApiException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGiphyApiException(GiphyApiException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error_view";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGenericException(Exception ex, Model model) {
        model.addAttribute("errorMessage", "An unexpected error occurred. Please try again later. Details: " + ex.getMessage());
        // It's good practice to log the exception here
        // log.error("Unexpected error occurred", ex); 
        return "error_view";
    }
}
