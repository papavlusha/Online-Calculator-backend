package by.spaces.calculator.controller;

import by.spaces.calculator.containers.ClientMessage;
import by.spaces.calculator.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import static by.spaces.calculator.logging.AppLogger.logError;
import static by.spaces.calculator.logging.AppLogger.logInfo;

@Controller
public class ChatController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;

    @Autowired
    public ChatController(SimpMessagingTemplate s, MessageService m){
        this.simpMessagingTemplate = s;
        this.messageService = m;
    }

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public ClientMessage receivePublicMessage(@Payload ClientMessage message){
        messageService.saveMessage(message);
        logInfo(ChatController.class, "Receive public message");
        return message;
    }

    @MessageMapping("/private-message")
    public ClientMessage receivePrivateMessage(@Payload ClientMessage message){
        try{
            simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
            messageService.saveMessage(message);
            logInfo(ChatController.class, "Receive private message");
        } catch (Exception e){
            logError(ChatController.class, "Unable to process the message: "  + e.getMessage());
        }
        return message;
    }
}
