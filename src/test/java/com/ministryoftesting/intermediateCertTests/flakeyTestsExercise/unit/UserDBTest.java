package com.ministryoftesting.intermediateCertTests.flakeyTestsExercise.unit;

import com.ministryoftesting.api.TimesheetManagerApplication;
import com.ministryoftesting.db.UserDB;
import com.ministryoftesting.models.user.User;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserDBTest {

    private static UserDB userDB;

    @BeforeAll
    public static void setup() {
        userDB = new UserDB();
    }

    @Test
    public void returnsPositiveResultWhenCreatingUser() throws SQLException {
        User user = new User("Jon", "test@email.com", "password", "user");
        User result = userDB.createUser(user);

        assertEquals(User.class, result.getClass());
    }

    @Test
    public void returnsPositiveResultWhenDeletingUser() throws SQLException {
        returnsPositiveResultWhenCreatingUser();
        int noOfUsers = userDB.getUsers().size();
        boolean result = userDB.deleteUser(noOfUsers);

        assertTrue(result);
    }

    @Test
    public void returnsNegativeResultWhenDeletingNonUser() throws SQLException {
        boolean result = userDB.deleteUser(122);

        assertFalse(result);
    }

    //failing because needs to have a user created first
    @Test
    public void returnsUserWhenGettingUserProfile() throws SQLException {
        User user = userDB.getUserProfile(1);

        assertEquals(User.class, user.getClass());
    }

    @Test
    public void returnsNegativeResponseWhenGettingNonUserProfile() throws SQLException {
        User user = userDB.getUserProfile(10);

        assertNull(user);
    }

    @Test
    public void returnsPositiveResultWhenUpdatingUser() throws SQLException {
        User updatedUser = new User("Sam", "update@update.com", "hello123", "admin");
        boolean result = userDB.updateUser(1, updatedUser);

        assertTrue(result);
    }

    @Test
    public void returnsUsersWhenRequested() throws SQLException {
        List<User> users = userDB.getUsers();

        assertFalse(users.isEmpty());
    }
}
