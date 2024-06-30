package io.hrushik09.ecommerce.auth;

import java.util.stream.Stream;

public class TestProperties {
    public static final String UUID_REGEX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    public static Stream<String> invalidAuthorityValueStrings() {
        return Stream.of("", "      ", ":", "a:", ":a", "!@#$%^&*()_+");
    }

    public static Stream<String> invalidUsernameStrings() {
        return Stream.of("", "   ", "9", "38792", "d&", "j%", "o#", "w@", "abcd1234_123efghp");
    }

    public static Stream<String> invalidEmailStrings() {
        return Stream.of("", "    ", "33", "a4uhjd", "kjbfkb_@", "knek2jbk@jnfka");
    }

    public static Stream<String> invalidPasswordStrings() {
        return Stream.of("", "   ", "abcde", "abcdefghijklm", ":;,.'\"");
    }
}
