package by.spaces.calculator.service;

import by.spaces.calculator.model.User;
import by.spaces.calculator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static by.spaces.calculator.logging.AppLogger.*;

@Service
public class AuthUserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService tokenProvider;

    @Autowired
    public AuthUserService(PasswordEncoder p, UserRepository u, AuthenticationManager a, JwtService j){
        passwordEncoder = p;
        userRepository = u;
        authenticationManager = a;
        tokenProvider = j;
    }

    public String loginUser(String username, String password) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return tokenProvider.generateToken(authentication);
    }

    public void registerUser(User user) {
        logInfo(AuthUserService.class, "registering user " + user.getUsername());

        if(userRepository.existsByLogin(user.getLogin())) {
            throw new UsernameAlreadyExistsException(
                    String.format("login %s already exists", user.getLogin()));
        }

        if(userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException(
                    String.format("email %s already exists", user.getEmail()));
        }
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        userRepository.save(user);
    }
}
