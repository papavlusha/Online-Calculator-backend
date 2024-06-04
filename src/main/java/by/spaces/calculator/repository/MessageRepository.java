package by.spaces.calculator.repository;

import by.spaces.calculator.model.Message;
import by.spaces.calculator.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
    @Transactional
    Message findMessageByMessageId(Long id);

    @Transactional
    Message findMessagesBySender(User u);

    @Transactional
    Message findMessagesByReceiver(User u);
}
