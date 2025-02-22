package muteshop.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    @Test
    void testUserGettersAndSetters() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");

        assertEquals("test@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
    }

    @Test
    void testUserConstructor() {
        User user = new User("admin@example.com", "securePass");

        assertEquals("admin@example.com", user.getEmail());
        assertEquals("securePass", user.getPassword());
    }

    @Test
    void testToStringMethod() {
        User user = new User("user@example.com", "testPass");
        String expected = "User{id=null, email='user@example.com', password='testPass'}";

        assertEquals(expected, user.toString());
    }
}
