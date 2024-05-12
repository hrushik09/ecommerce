package io.hrushik09.ecommerce.inventory.web.exceptions;

public class AlreadyExists extends RuntimeException {
    public AlreadyExists(String message) {
        super(message);
    }
}
