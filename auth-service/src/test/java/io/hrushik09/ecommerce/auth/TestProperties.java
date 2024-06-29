package io.hrushik09.ecommerce.auth;

import java.util.stream.Stream;

class TestProperties {
    public static Stream<String> invalidAuthorityValueStrings() {
        return Stream.of("", "      ", ":", "a:", ":a", "!@#$%^&*()_+");
    }
}
