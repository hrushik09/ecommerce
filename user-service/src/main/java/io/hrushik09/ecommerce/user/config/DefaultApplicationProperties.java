package io.hrushik09.ecommerce.user.config;

public class DefaultApplicationProperties {
    public static final String VALID_USERNAME_REGEX = "^[A-Za-z][\\w]{0,15}$";
    public static final String VALID_EMAIL_REGEX = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    public static final String VALID_FIRST_NAME_REGEX = "^[a-zA-Z][a-zA-Z ]{1,99}$";
    public static final String VALID_LAST_NAME_REGEX = "^[a-zA-Z][a-zA-Z ]{1,99}$";
    public static final String VALID_COUNTRY_REGEX = "^[a-zA-Z][a-zA-Z ]{1,99}$";
    public static final String VALID_REGION_REGEX = "^[a-zA-Z][a-zA-Z ]{1,99}$";
}
