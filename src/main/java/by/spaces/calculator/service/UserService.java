package by.spaces.calculator.service;

import by.spaces.calculator.model.User;
import by.spaces.calculator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static by.spaces.calculator.logging.AppLogger.logInfo;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository u){
        userRepository = u;
    }

    public List<User> findAll() {
        logInfo(AuthUserService.class, "retrieving all users");
        return (List<User>) userRepository.findAll();
    }

    public User findByLogin(String login) {
        logInfo(AuthUserService.class, "retrieving user " + login);
        return userRepository.findUserByLogin(login);
    }

    public User findById(Long id) {
        logInfo(AuthUserService.class, "retrieving user " + id);
        return userRepository.findUserByUserId(id);
    }
}
