package main.test.java;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthService {
    public boolean login(String username, String password) {
        return "user".equals(username) && "pass123".equals(password);
    }
}

public class AuthServiceTest {
    @Test
    void testLogin() {
        AuthService authService = new AuthService();
        assertTrue(authService.login("user", "pass123"));
        assertFalse(authService.login("user", "wrongpass"));
    }
}
