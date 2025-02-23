package muteshop.controllers;

import muteshop.models.User;
import muteshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            if (userService.findByEmail(user.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Email already in use"));
            }

            user.setEmail(user.getEmail().toLowerCase());

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            User registeredUser = userService.registerUser(user);
            return ResponseEntity.ok(Map.of("message", "User registered successfully", "userId", registeredUser.getId()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Internal Server Error"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<User> foundUser = userService.findByEmail(user.getEmail());

        if (foundUser.isPresent()) {
            User existingUser = foundUser.get();

            // Debug: Print stored password and input password
            System.out.println("Stored Password Hash: " + existingUser.getPassword());
            System.out.println("Raw Input Password: " + user.getPassword());

            boolean passwordMatches = passwordEncoder.matches(user.getPassword(), existingUser.getPassword());
            System.out.println("Password Matches? " + passwordMatches);

            if (passwordMatches) {
                return ResponseEntity.ok(Map.of("message", "Login successful", "userId", existingUser.getId()));
            }
        }

        return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
    }


    @GetMapping("/test-hash")
    public ResponseEntity<?> testHash() {
        String rawPassword = "yourpassword"; // Replace with an actual password
        String hashedPassword = passwordEncoder.encode(rawPassword);
        return ResponseEntity.ok(Map.of("hashedPassword", hashedPassword));
    }

}
