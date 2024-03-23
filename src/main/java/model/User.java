package model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "users")
@NamedQuery(
        name = "getAllAdmins",
        query = "SELECT u FROM User u WHERE u.isAdmin = true"
)
@NamedQuery(
        name = "getUserById",
        query = "SELECT u FROM User u WHERE u.userId = :userId"
)
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
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Getter
    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Getter
    @Setter
    @Column(name = "username")
    private String username;

    @Getter
    @Setter
    @Column(name = "gender")
    private String gender;

    @Getter
    @Setter
    @Column(name = "about")
    private String about;

    @Getter
    @Setter
    @Column(name = "solved_tasks")
    private int solvedTasks;

    @Getter
    @Setter
    @Column(name = "photo")
    @Lob
    private byte[] photo;

    @Column(name = "last_activity", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp lastActivity;

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
    public User(String login, String email, String userPassword, String username,
                String gender, String about, int solvedTasks, byte[] photo,
                Timestamp lastActivity, Boolean isAdmin, Boolean isBlocked, Timestamp createdAt) {
        this.login = login;
        this.email = email;
        this.userPassword = userPassword;
        this.username = username;
        this.gender = gender;
        this.about = about;
        this.solvedTasks = solvedTasks;
        this.photo = photo;
        this.lastActivity = lastActivity;
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
            return new User(login, email, userPassword, username, gender, about,
                    solvedTasks, photo, lastActivity, isAdmin, isBlocked, createdAt);
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
        return copyTimestamp(createdAt);
    }

    public void setCreatedAt(Timestamp time) {
        this.createdAt = copyTimestamp(time);
    }

    public Timestamp getLastActivity() {
        return copyTimestamp(lastActivity);
    }

    public void setLastActivity(Timestamp time) {
        this.lastActivity = copyTimestamp(time);
    }

    private Timestamp copyTimestamp(Timestamp time) {
        if (time != null) {
            return new Timestamp(time.getTime());
        }
        return null;
    }

    @Override
    public String toString() {
        return "User [id=" + userId + ", login=" + login + ", username=" + username + "]";
    }
}
