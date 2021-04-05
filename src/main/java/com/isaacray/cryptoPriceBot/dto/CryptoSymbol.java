package com.isaacray.cryptoPriceBot.dto;

public class CryptoSymbol {
    private String id;
    private String symbol;
    private String name;

    public CryptoSymbol() {
    }

    public CryptoSymbol(String id, String symbol, String name) {
        this.id = id;
        this.symbol = symbol;
        this.name = name;
    }

    @Override
    public String toString() {
        return "CryptoSymbols{" +
                "id='" + id + '\'' +
                ", symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}