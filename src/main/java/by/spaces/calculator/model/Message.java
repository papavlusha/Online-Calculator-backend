package by.spaces.calculator.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "messages")
@Builder(toBuilder = true)
public class Message {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long messageId;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Getter
    @Setter
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at")
    private Timestamp createdAt;

    protected Message() {
        //Empty
    }

    public static class MessageBuilder {
        public Message build() {
            if (sender == null || receiver == null ||content == null) {
                throw new IllegalArgumentException("User and content must be provided");
            }
            Message mes = new Message();
            mes.setSender(sender);
            mes.setReceiver(receiver);
            mes.setContent(content);
            mes.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            return mes;
        }
    }

    public void setCreatedAt(Timestamp time) {
        this.createdAt = new Timestamp(time.getTime());
    }
}
