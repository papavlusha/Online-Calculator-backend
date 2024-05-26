package by.spaces.calculator.service;

import by.spaces.calculator.containers.ClientMessage;
import by.spaces.calculator.model.Message;
import by.spaces.calculator.repository.MessageRepository;
import by.spaces.calculator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static by.spaces.calculator.logging.AppLogger.logError;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public void saveMessage(ClientMessage clientMessage) {
        try{
            Message message = Message.builder()
                    .sender(userRepository.findUserByLogin(clientMessage.getSenderName()))
                    .receiver(userRepository.findUserByLogin(clientMessage.getReceiverName()))
                    .content(clientMessage.getMessage())
                    .build();
            messageRepository.save(message);
        } catch (Exception e){
            throw new IllegalArgumentException("Wrong message data");
        }
    }
}
