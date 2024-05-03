package io.hrushik09.ecommerce.inventory;

import java.util.stream.Stream;

class ParameterizedTestParams {
    public static Stream<String> blankStrings() {
        return Stream.of(null, "", "      ");
    }
}
