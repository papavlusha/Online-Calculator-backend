package by.spaces.calculator.containers;

import by.spaces.calculator.model.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@Schema(description = "Message data container")
public class ClientMessage {
    @Schema(description = "Sender's login", example = "Alice")
    @NotNull
    private String senderName;
    @Schema(description = "Receiver's login", example = "Bob")
    private String receiverName;
    @Schema(description = "Message content", example = "Hello!")
    @NotNull
    private String message;
    @Schema(description = "Message date", example = "2024-05-26 22:46:37")
    @NotNull
    private String date;
    @Schema(description = "Message status", example = "JOIN")
    @NotNull
    private Status status;
}
