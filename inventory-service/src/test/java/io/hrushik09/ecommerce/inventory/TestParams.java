package io.hrushik09.ecommerce.inventory;

import java.util.stream.Stream;

public class TestParams {
    public static final String stringWithLength100 = "oqhabsdkjboqiwoakndajbskbkajsbfieyrgacekjbhsjasfjwjekabskasafasdiqrbabsahsbfkasbfqwijasnkajbsfkabssd";
    public static final String stringWithLength101 = "oqhabsdkjboqiwoakndajbskbkajsbfieyrgacekjbhsjasfjwjekabskasafasdiqrbabsahsbfkasbfqwijasnkajbsfkabssdw";

    public static Stream<String> blankStrings() {
        return Stream.of(null, "", "      ");
    }

    public static Stream<String> invalidSimpleStrings() {
        return Stream.of("~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "+", "=", "{", "[", "}", "]",
                "|", "\\", ":", ";", "\"", "'", "<", ",", ">", ".", "?", "/"
        );
    }
}
