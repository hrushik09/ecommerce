package io.hrushik09.ecommerce.catalog.web.exceptions;

public class DoesNotExist extends RuntimeException {
    public DoesNotExist(String message) {
        super(message);
    }
}
