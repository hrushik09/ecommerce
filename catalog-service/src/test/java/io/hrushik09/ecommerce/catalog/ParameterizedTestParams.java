package io.hrushik09.ecommerce.catalog;

import java.util.stream.Stream;

class ParameterizedTestParams {
    public static Stream<String> blankStrings() {
        return Stream.of(null, "", "      ");
    }
}