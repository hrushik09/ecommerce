package io.hrushik09.ecommerce.inventory;

import java.util.stream.Stream;

public class TestProperties {
    public static final String STRING_WITH_LENGTH_100 = "oqhabsdkjboqiwoakndajbskbkajsbfieyrgacekjbhsjasfjwjekabskasafasdiqrbabsahsbfkasbfqwijasnkajbsfkabssd";
    public static final String STRING_WITH_LENGTH_101 = "oqhabsdkjboqiwoakndajbskbkajsbfieyrgacekjbhsjasfjwjekabskasafasdiqrbabsahsbfkasbfqwijasnkajbsfkabssdw";
    public static final String STRING_WITH_LENGTH_500 = "oqhabsdkjboqiwoakndajbskbkajsbfieyrgacekjbhsjasfjwjekabskasafasdiqrbabsahsbfkasbfqwijasnkajbsfkabssdoqhabsdkjboqiwoakndajbskbkajsbfieyrgacekjbhsjasfjwjekabskasafasdiqrbabsahsbfkasbfqwijasnkajbsfkabssdoqhabsdkjboqiwoakndajbskbkajsbfieyrgacekjbhsjasfjwjekabskasafasdiqrbabsahsbfkasbfqwijasnkajbsfkabssdoqhabsdkjboqiwoakndajbskbkajsbfieyrgacekjbhsjasfjwjekabskasafasdiqrbabsahsbfkasbfqwijasnkajbsfkabssdoqhabsdkjboqiwoakndajbskbkajsbfieyrgacekjbhsjasfjwjekabskasafasdiqrbabsahsbfkasbfqwijasnkajbsfkabssd";
    public static final String STRING_WITH_LENGTH_501 = "oqhabsdkjboqiwoakndajbskbkajsbfieyrgacekjbhsjasfjwjekabskasafasdiqrbabsahsbfkasbfqwijasnkajbsfkabssdoqhabsdkjboqiwoakndajbskbkajsbfieyrgacekjbhsjasfjwjekabskasafasdiqrbabsahsbfkasbfqwijasnkajbsfkabssdoqhabsdkjboqiwoakndajbskbkajsbfieyrgacekjbhsjasfjwjekabskasafasdiqrbabsahsbfkasbfqwijasnkajbsfkabssdoqhabsdkjboqiwoakndajbskbkajsbfieyrgacekjbhsjasfjwjekabskasafasdiqrbabsahsbfkasbfqwijasnkajbsfkabssdoqhabsdkjboqiwoakndajbskbkajsbfieyrgacekjbhsjasfjwjekabskasafasdiqrbabsahsbfkasbfqwijasnkajbsfkabssdu";
    public static final String INVALID_CHARACTERS_FOR_SIMPLE_TEXT = "~!@#$%^&*()+={[}]|\\:;\"'<,>.?/";
    public static final String DEFAULT_TIMESTAMP_REGEX = "[a-zA-Z]+ \\d{1,2} \\d{4}, \\d{2}:\\d{2}:\\d{2} \\(UTC[+-]\\d{2}:\\d{2}\\)";
    public static final String UUID_REGEX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    public static Stream<String> blankStrings() {
        return Stream.of(null, "", "      ");
    }
}
