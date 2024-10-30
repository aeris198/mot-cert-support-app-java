package com.ministryoftesting.foundationCertTests.failingchecks;

import com.ministryoftesting.db.AuthDB;
import com.ministryoftesting.models.auth.Credentials;
import com.ministryoftesting.models.auth.LoginResult;
import com.ministryoftesting.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class UnitFailingChecks {

    // The @Mock annotation creates a mock object of AuthDB for testing purposes.
    @Mock
    private AuthDB authDB;

    // The @Autowired and @InjectMocks annotations inject the mock objects into AuthService for testing.
    @Autowired
    @InjectMocks
    private AuthService authService;

    // The @BeforeEach annotation signifies that the following method will be executed before each test.
    @BeforeEach
    public void initialiseMocks() {
        // This line initializes the mock objects using Mockito framework.
        MockitoAnnotations.openMocks(this);
    }

    // This is a test method named testIncorrectLogin that checks the behavior of AuthService for an incorrect login.
    @Test
    public void testIncorrectLogin() throws SQLException {
        // Mocks the behavior of authDB.checkLogin() method for a fake login attempt.
        when(authDB.checkLogin("fake@email.com", "password")).thenReturn(new LoginResult(false, null, 0));

        // Calls the login method of authService with fake credentials.
        ResponseEntity<Credentials> response = authService.login("fake@email.com", "password");

        // Verifies that the authDB.checkLogin() method is called exactly once with specified arguments.
        verify(authDB, times(1)).checkLogin("fake@email.com", "password");
    }

    // This is another test method named testValidTokenReturnsOk to validate a session token.
    @Test
    public void testValidTokenReturnsOk() throws SQLException {
        // Mocks the behavior of authDB.checkSession() method for a valid session token.
        when(authDB.checkSession("abc123", LocalDate.of(3001, 1, 1))).thenReturn(true);

        // Calls the validate method of authService with a token and date.
        boolean response = authService.validate("abc123", LocalDate.of(3001, 1, 1));

        // Asserts that the response from authService.validate() is true.
        assertTrue(response);
    }
}

