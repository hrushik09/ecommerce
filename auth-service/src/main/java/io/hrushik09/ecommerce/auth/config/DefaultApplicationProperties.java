package io.hrushik09.ecommerce.auth.config;

public class DefaultApplicationProperties {
    public static final String VALID_AUTHORITY_VALUE_REGEX = "^[A-Za-z][A-Za-z:]*[A-Za-z]$";
    public static final String VALID_USERNAME_REGEX = "^[A-Za-z][\\w]{0,15}$";
    public static final String VALID_EMAIL_REGEX = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    public static final String VALID_PASSWORD_REGEX = "[\\w~`@#$%^&*()_+={}\\[\\]\\\\\\|\\?<>-]{6,12}";
}
