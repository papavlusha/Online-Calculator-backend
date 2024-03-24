package by.spaces.calculator.service;

import by.spaces.calculator.model.User;
import by.spaces.calculator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CalculatorUserDetailsService implements UserDetailsService {
    private final UserRepository userRepo;

    @Autowired
    public CalculatorUserDetailsService(UserRepository u){
        userRepo = u;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepo.findUserByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("Login not found");
        }
        return (UserDetails) user;
    }
}
