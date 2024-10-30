package com.ministryoftesting.intermediateCertTests.valueExercise;

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

    // not sure how useful this one is, a method of a class should work - delete?
    @Test
    public void checkClassCanBeCreated(){
        assertEquals(UserDB.class, userDB.getClass());
    }

    //update name of test?
    @Test
    public void testUser() throws SQLException {
        User user = new User("Jon", "test@email.com", "password", "user");
        User result = userDB.createUser(user);

        assertEquals(User.class, result.getClass());
    }

    //Passes if True, but misleading, same with False one below?
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

    //Starts with id = 2, so fails, testUser class above has created an entry
    @Test
    public void testUserList() throws SQLException {
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
