package by.spaces.calculator.service;

import by.spaces.calculator.model.User;
import by.spaces.calculator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static by.spaces.calculator.logging.AppLogger.*;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserService(PasswordEncoder p, UserRepository u, AuthenticationManager a){
        passwordEncoder = p;
        userRepository = u;
        authenticationManager = a;
    }

    public void loginUser(String username, String password) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        authentication.isAuthenticated();
    }

    public User registerUser(User user) {
        logInfo(UserService.class, "registering user " + user.getUsername());

        if(userRepository.existsByLogin(user.getLogin())) {
            logWarn(UserService.class, "login " + user.getLogin() + " already exists.");

            throw new UsernameAlreadyExistsException(
                    String.format("username %s already exists", user.getUsername()));
        }

        if(userRepository.existsByEmail(user.getEmail())) {
            logWarn(UserService.class, "email " + user.getEmail() + " already exists.");

            throw new EmailAlreadyExistsException(
                    String.format("email %s already exists", user.getEmail()));
        }
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        return userRepository.save(user);
    }

    public List<User> findAll() {
        logInfo(UserService.class, "retrieving all users");
        return (List<User>) userRepository.findAll();
    }

    public User findByLogin(String login) {
        logInfo(UserService.class, "retrieving user " + login);
        return userRepository.findUserByLogin(login);
    }

    public User findById(Long id) {
        logInfo(UserService.class, "retrieving user " + id);
        return userRepository.findUserByUserId(id);
    }
}
