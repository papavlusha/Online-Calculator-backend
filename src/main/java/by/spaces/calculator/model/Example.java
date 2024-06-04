package by.spaces.calculator.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "examples")
@Builder(toBuilder = true)
public class Example {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "example_id")
    private Long exampleId;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Getter
    @Setter
    @Column(name = "content", nullable = false)
    private String content;

    @Getter
    @Setter
    @Column(name = "result", nullable = false)
    private String result;

    @Column(name = "created_at")
    private Timestamp createdAt;

    protected Example() {
        //Empty
    }

    public static class ExampleBuilder {
        public Example build() {
            if (user == null || content == null || result == null) {
                throw new IllegalArgumentException("User, content and result must be provided");
            }
            Example ex = new Example();
            ex.setUser(user);
            ex.setContent(content);
            ex.setResult(result);
            ex.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            return ex;
        }
    }

    public void setCreatedAt(Timestamp time) {
        this.createdAt = new Timestamp(time.getTime());
    }
}
