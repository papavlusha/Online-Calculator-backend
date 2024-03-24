package by.spaces.calculator.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "users")
@Builder(toBuilder = true)
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

    @Column(name = "last_activity")
    private Timestamp lastActivity;

    @Getter
    @Setter
    @Column(name = "is_admin", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isAdmin;

    @Getter
    @Setter
    @Column(name = "is_blocked", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isBlocked;

    @Column(name = "created_at")
    private Timestamp createdAt;

    protected User() {

    }

    public static class UserBuilder {
        public User build() {
            if (login == null || email == null || userPassword == null) {
                throw new IllegalArgumentException("Login, email and userPassword must be provided");
            }
            User user = new User();
            user.setLogin(login);
            user.setEmail(email);
            user.setUserPassword(userPassword);
            user.setUsername(username);
            user.setGender(gender);
            user.setAbout(about);
            user.setSolvedTasks(solvedTasks);
            user.setPhoto(photo);
            user.setLastActivity(new Timestamp(System.currentTimeMillis()));
            user.setAdmin(isAdmin);
            user.setBlocked(isBlocked);
            user.setCreatedAt(createdAt != null ? createdAt : new Timestamp(System.currentTimeMillis()));
            return user;
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
