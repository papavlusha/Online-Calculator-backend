package by.spaces.calculator.repository;

import by.spaces.calculator.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Transactional
    User findUserByUserId(Long id);

    @Transactional
    User findUserByLogin(String login);

    @Transactional
    List<User> findUsersByAdminTrue();

    @Transactional
    default User saveAndReturnUser(User user) {
        return save(user);
    }

    boolean existsByLogin(String login);

    boolean existsByEmail(String email);
}