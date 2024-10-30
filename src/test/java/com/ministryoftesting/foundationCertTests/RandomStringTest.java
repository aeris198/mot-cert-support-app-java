package com.ministryoftesting.foundationCertTests;

import com.ministryoftesting.service.RandomString;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RandomStringTest {

    @Test
    public void testRandomStringCanBeCreated() {
        // Arrange
        RandomString randomString = new RandomString(10);

        // Act
        String createdString = randomString.nextString();

        // Assert
        assertEquals(10, createdString.length());

    }
}
