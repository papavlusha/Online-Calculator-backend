package by.spaces.calculator.controller;

import by.spaces.calculator.containers.ApiResponse;
import by.spaces.calculator.containers.LoginRequest;
import by.spaces.calculator.containers.SignUpRequest;
import by.spaces.calculator.model.User;
import by.spaces.calculator.service.EmailAlreadyExistsException;
import by.spaces.calculator.service.UserService;
import by.spaces.calculator.service.UsernameAlreadyExistsException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static by.spaces.calculator.logging.AppLogger.logInfo;

@RestController
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService u){
        userService = u;
    }

    @PostMapping("/signin")
    public ResponseEntity<Void> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        userService.loginUser(loginRequest.getLogin(), loginRequest.getPassword());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody SignUpRequest data) {
        logInfo(AuthController.class, "creating user " + data.getUsername());

        User user = User
                .builder()
                .login(data.getLogin())
                .email(data.getEmail())
                .userPassword(data.getPassword())
                .build();

        try {
            userService.registerUser(user);
        } catch (UsernameAlreadyExistsException | EmailAlreadyExistsException e) {
            throw new BadRequestException(e.getMessage());
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/signup/{login}")
                .buildAndExpand(user.getLogin()).toUri();

        return ResponseEntity
                .created(location)
                .body(new ApiResponse(true,"User registered successfully"));
    }
}
