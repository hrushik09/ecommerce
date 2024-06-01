package io.hrushik09.ecommerce.catalog.domain.listings;

import java.util.stream.Stream;

public enum Currency {
    INR("INR"),
    USD("USD"),
    CAD("CAD"),
    EUR("EUR");

    private final String code;

    Currency(String code) {
        this.code = code;
    }

    public static Currency of(String code) {
        return Stream.of(Currency.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("invalid code " + code + " received"));
    }

    public String getCode() {
        return code;
    }
}
