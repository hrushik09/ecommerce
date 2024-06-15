package io.hrushik09.ecommerce.user.web.customer;

import io.hrushik09.ecommerce.user.AbstractEndToEndTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

class CustomerEndToEndTest extends AbstractEndToEndTest {
    @Nested
    class CreateCustomer {
        @Test
        void shouldCreateCustomerSuccessfully() {
            given()
                    .contentType(JSON)
                    .body("""
                            {
                            "username": "user1_2",
                            "email": "user1_2@gmail.com",
                            "firstName": "Jane",
                            "lastName": "Doe",
                            "country": "India",
                            "region": "West"
                            }
                            """)
                    .when()
                    .post("/api/customers")
                    .then()
                    .statusCode(CREATED.value())
                    .body("code", hasLength(4 + 1 + 36))
                    .body("code", startsWith("user_"))
                    .body("username", equalTo("user1_2"))
                    .body("email", equalTo("user1_2@gmail.com"))
                    .body("firstName", equalTo("John"))
                    .body("lastName", equalTo("Doe"))
                    .body("country", equalTo("India"))
                    .body("region", equalTo("West"));
        }

        @Test
        void shouldNotCreateWhenCustomerExistsForUsername() {
            given()
                    .contentType(JSON)
                    .body("""
                            {
                            "username": "user33",
                            "email": "randommail@gmail.com",
                            "firstName": "doesnt matter",
                            "lastName": "doesnt matter",
                            "country": "doesnt matter",
                            "region": "doesnt matter"
                            }
                            """)
                    .when()
                    .post("/api/customers")
                    .then()
                    .statusCode(CREATED.value());

            given()
                    .contentType(JSON)
                    .body("""
                            {
                            "username": "user33",
                            "email": "othermail@gmail.com",
                            "firstName": "doesnt matter",
                            "lastName": "doesnt matter",
                            "country": "doesnt matter",
                            "region": "doesnt matter"
                            }
                            """)
                    .when()
                    .post("/api/customers")
                    .then()
                    .statusCode(BAD_REQUEST.value())
                    .body("detail", equalTo("Customer with username user33 already exists"));
        }

        @Test
        void shouldNotCreateWhenCustomerExistsForEmail() {
            given()
                    .contentType(JSON)
                    .body("""
                            {
                            "username": "randomusername",
                            "email": "user13@gmail.com",
                            "firstName": "doesnt matter",
                            "lastName": "doesnt matter",
                            "country": "doesnt matter",
                            "region": "doesnt matter"
                            }
                            """)
                    .when()
                    .post("/api/customers")
                    .then()
                    .statusCode(CREATED.value());

            given()
                    .contentType(JSON)
                    .body("""
                            {
                            "username": "otherusername",
                            "email": "user13@gmail.com",
                            "firstName": "doesnt matter",
                            "lastName": "doesnt matter",
                            "country": "doesnt matter",
                            "region": "doesnt matter"
                            }
                            """)
                    .when()
                    .post("/api/customers")
                    .then()
                    .statusCode(BAD_REQUEST.value())
                    .body("detail", equalTo("Customer with email user13@gmail.com already exists"));
        }
    }
}
