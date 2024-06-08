package io.hrushik09.ecommerce.inventory;

import java.util.stream.Stream;

public class TestParams {
    public static final String STRING_WITH_LENGTH_100 = "oqhabsdkjboqiwoakndajbskbkajsbfieyrgacekjbhsjasfjwjekabskasafasdiqrbabsahsbfkasbfqwijasnkajbsfkabssd";
    public static final String STRING_WITH_LENGTH_101 = "oqhabsdkjboqiwoakndajbskbkajsbfieyrgacekjbhsjasfjwjekabskasafasdiqrbabsahsbfkasbfqwijasnkajbsfkabssdw";
    public static final String STRING_WITH_LENGTH_500 = "oqhabsdkjboqiwoakndajbskbkajsbfieyrgacekjbhsjasfjwjekabskasafasdiqrbabsahsbfkasbfqwijasnkajbsfkabssdoqhabsdkjboqiwoakndajbskbkajsbfieyrgacekjbhsjasfjwjekabskasafasdiqrbabsahsbfkasbfqwijasnkajbsfkabssdoqhabsdkjboqiwoakndajbskbkajsbfieyrgacekjbhsjasfjwjekabskasafasdiqrbabsahsbfkasbfqwijasnkajbsfkabssdoqhabsdkjboqiwoakndajbskbkajsbfieyrgacekjbhsjasfjwjekabskasafasdiqrbabsahsbfkasbfqwijasnkajbsfkabssdoqhabsdkjboqiwoakndajbskbkajsbfieyrgacekjbhsjasfjwjekabskasafasdiqrbabsahsbfkasbfqwijasnkajbsfkabssd";
    public static final String STRING_WITH_LENGTH_501 = "oqhabsdkjboqiwoakndajbskbkajsbfieyrgacekjbhsjasfjwjekabskasafasdiqrbabsahsbfkasbfqwijasnkajbsfkabssdoqhabsdkjboqiwoakndajbskbkajsbfieyrgacekjbhsjasfjwjekabskasafasdiqrbabsahsbfkasbfqwijasnkajbsfkabssdoqhabsdkjboqiwoakndajbskbkajsbfieyrgacekjbhsjasfjwjekabskasafasdiqrbabsahsbfkasbfqwijasnkajbsfkabssdoqhabsdkjboqiwoakndajbskbkajsbfieyrgacekjbhsjasfjwjekabskasafasdiqrbabsahsbfkasbfqwijasnkajbsfkabssdoqhabsdkjboqiwoakndajbskbkajsbfieyrgacekjbhsjasfjwjekabskasafasdiqrbabsahsbfkasbfqwijasnkajbsfkabssdu";

    public static Stream<String> blankStrings() {
        return Stream.of(null, "", "      ");
    }

    public static Stream<String> invalidSimpleStrings() {
        return Stream.of("~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "+", "=", "{", "[", "}", "]",
                "|", "\\", ":", ";", "\"", "'", "<", ",", ">", ".", "?", "/"
        );
    }
}
