package com.ministryoftesting.intermediateCertTests.valueExerciseUpdated;

import com.ministryoftesting.db.UserDB;
import com.ministryoftesting.models.user.User;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ChecksToAnalyseTest {

    private static UserDB userDB;

    @BeforeAll
    public static void setup() {
        userDB = new UserDB();
    }

    @Test
    public void checkClassCanBeCreated(){
        assertEquals(UserDB.class, userDB.getClass());
    }

    @Test
    public void userCanBeCreated() throws SQLException {
        User user = new User("Jon", "test@email.com", "password", "user");
        User result = userDB.createUser(user);

        assertEquals(User.class, result.getClass());
    }

    @Test
    public void returnsPositiveResultWhenDeletingUser() throws SQLException {
        boolean result = userDB.deleteUser(1);

        assertTrue(result);
    }

    @Test
    public void returnsNegativeResultWhenDeletingNonUser() throws SQLException {
        boolean result = userDB.deleteUser(122);

        assertFalse(result);
    }

    @Test
    public void testUserList() throws SQLException {

        //cleardown any existing projects in the DB
        for (int i = userDB.getUsers().size(); i > 0; i--) {
            userDB.deleteUser(i);
        }

        User user = new User("Jon", "test@email.com", "password", "user");
        userDB.createUser(user);
        User user2 = new User("Jon", "test@email.com", "password", "user");
        userDB.createUser(user);
        User user3 = new User("Jon", "test@email.com", "password", "user");
        userDB.createUser(user);

        List<User> users = userDB.getUsers();

        Approvals.verify(users.toString());
    }

}
