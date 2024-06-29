package io.hrushik09.ecommerce.auth;

import java.util.stream.Stream;

public class TestProperties {
    public static final String UUID_REGEX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    public static Stream<String> invalidAuthorityValueStrings() {
        return Stream.of("", "      ", ":", "a:", ":a", "!@#$%^&*()_+");
    }
}
