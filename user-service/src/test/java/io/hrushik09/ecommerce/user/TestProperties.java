package io.hrushik09.ecommerce.user;

import java.util.stream.Stream;

public class TestProperties {
    public static final String UUID_REGEX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    public static Stream<String> invalidUsernameStrings() {
        return Stream.of("", "   ", "9", "38792", "d&", "j%", "o#", "w@", "abcd1234_123efghp");
    }

    public static Stream<String> invalidEmailStrings() {
        return Stream.of("", "    ", "33", "a4uhjd", "kjbfkb_@", "knek2jbk@jnfka");
    }

    public static Stream<String> invalidFirstNameStrings() {
        return Stream.of("", "    ", "38293", "32jasa8734", "aas_akejnkzn", "ajkb_qjks3q4q", "alsknaskjbdkaakbfkasjfkasbk kabznx caksfbaksjfaksbfkajs asdknaks a jfanflasnfsjfajbfkajbfkjakasdasdas");
    }

    public static Stream<String> invalidLastNameStrings() {
        return Stream.of("", "    ", "38293", "32jasa8734", "aas_akejnkzn", "ajkb_qjks3q4q", "alsknaskjbdkaakbfkasjfkasbk kabznx caksfbaksjfaksbfkajs asdknaks a jfanflasnfsjfajbfkajbfkjakasdasdas");
    }

    public static Stream<String> invalidCountryStrings() {
        return Stream.of("", "    ", "38293", "32jasa8734", "aas_akejnkzn", "ajkb_qjks3q4q", "alsknaskjbdkaakbfkasjfkasbk kabznx caksfbaksjfaksbfkajs asdknaks a jfanflasnfsjfajbfkajbfkjakasdasdas");
    }

    public static Stream<String> invalidRegionStrings() {
        return Stream.of("", "    ", "38293", "32jasa8734", "aas_akejnkzn", "ajkb_qjks3q4q", "alsknaskjbdkaakbfkasjfkasbk kabznx caksfbaksjfaksbfkajs asdknaks a jfanflasnfsjfajbfkajbfkjakasdasdas");
    }
}