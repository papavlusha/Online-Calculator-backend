package model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(
                name = "getAllAdmins",
                query = "SELECT u FROM User u WHERE u.isAdmin = true"
        ),
        @NamedQuery(
                name = "getUserById",
                query = "SELECT u FROM User u WHERE u.userId = :userId"
        )
})
public class User{
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Getter
    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Getter
    @Setter
    @Column(name = "username")
    private String username;

    @Getter
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Getter
    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Getter
    @Setter
    @Column(name = "is_admin", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isAdmin;

    @Getter
    @Setter
    @Column(name = "is_blocked", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isBlocked;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Builder(toBuilder = true)
    public User(String login, String email, String userPassword, String username, Boolean isAdmin, Boolean isBlocked, Timestamp createdAt) {
        this.login = login;
        this.email = email;
        this.userPassword = userPassword;
        this.username = username;
        this.isAdmin = isAdmin != null && isAdmin;
        this.isBlocked = isBlocked != null && isBlocked;
        this.createdAt = createdAt;
    }

    public User() {

    }

    public static class UserBuilder {
        public User build() {
            if (login == null || email == null || userPassword == null) {
                throw new IllegalArgumentException("Login, email and userPassword must be provided");
            }
            return new User(login, email, userPassword, username, isAdmin, isBlocked, createdAt);
        }
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Timestamp getCreatedAt() {
        if (createdAt != null) {
            return new Timestamp(createdAt.getTime());
        }
        return null;
    }

    public void setCreatedAt(Timestamp createdAt) {
        if (createdAt != null) {
            this.createdAt = new Timestamp(createdAt.getTime());
        }
    }

    @Override
    public String toString() {
        return "User [id=" + userId + ", login=" + login + ", username=" + username + "]";
    }
}
