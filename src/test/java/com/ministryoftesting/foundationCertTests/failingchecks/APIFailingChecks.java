package com.ministryoftesting.foundationCertTests.failingchecks;

import com.ministryoftesting.models.AuthPayload;
import com.ministryoftesting.api.TimesheetManagerApplication;
import com.ministryoftesting.models.auth.Credentials;
import com.ministryoftesting.models.user.User;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

// Define a class named APIFailingChecks to contain methods for API testing.
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = TimesheetManagerApplication.class)
@ActiveProfiles("dev")
public class APIFailingChecks {

    private String token; // Private variable to store authentication token.

    @BeforeEach // Annotation indicating that this method runs before each test.
    public void getToken() {
        AuthPayload authPayload = new AuthPayload("admin@test.com", "password123");
        // Retrieves credentials by sending a POST request to the login endpoint.
        // The credentials consist of an email and password provided in JSON format.
        Credentials credentials = given()
                .body(authPayload)
                .contentType("application/json")
                .post("http://localhost:8080/v1/auth/login")
                .as(Credentials.class);

        // Stores the authentication token obtained from the credentials.
        token = credentials.getToken();
    }

    @Test // Annotation indicating that this method is a test case.
    public void testUserDetails() {
        // Sends a GET request to retrieve user details using the stored token for authorization.
        // Expects the returned User object's username to be "administrator".
        User user = given()
                .header("Authorization", "Bearer " + token)
                .get("/v1/user/1")
                .as(User.class);

        assertEquals("administrator", user.getUsername()); // Asserts the retrieved username.
    }

    @Test // Annotation indicating another test case.
    public void positiveResponseWhenDeletingUser() {
        // Creates a new user by sending a POST request with user details in JSON format.
        // Uses the stored token for authorization in the header.
        User user = given()
                .body(new User("administrator", "admin@test.com", "password123", "admin"))
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .post("http://localhost:8080/v1/user")
                .as(User.class);

        // Sends a DELETE request to delete the user using the stored user's ID in the URL.
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .delete("/v1/user/" + user.getId());

        assertEquals(202, response.getStatusCode()); // Asserts the expected HTTP status code.
    }
}

