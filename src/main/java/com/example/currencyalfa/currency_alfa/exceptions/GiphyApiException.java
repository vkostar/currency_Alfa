package com.example.currencyalfa.currency_alfa.exceptions;

public class GiphyApiException extends RuntimeException {

    public GiphyApiException(String message) {
        super(message);
    }

    public GiphyApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
