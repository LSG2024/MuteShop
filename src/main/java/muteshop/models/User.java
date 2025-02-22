package muteshop.models;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // ✅ Constructor
    public User() {}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // ✅ Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // ✅ toString() (For debugging)
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    // ✅ TEST MAIN METHOD
    public static void main(String[] args) {
        User testUser = new User("test@example.com", "password123");
        System.out.println("Email: " + testUser.getEmail());
        System.out.println("Password: " + testUser.getPassword());
    }
}
