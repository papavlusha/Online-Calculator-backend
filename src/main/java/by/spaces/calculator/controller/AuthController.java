package by.spaces.calculator.controller;

import by.spaces.calculator.containers.ApiResponseContainer;
import by.spaces.calculator.containers.LoginRequest;
import by.spaces.calculator.containers.SignUpRequest;
import by.spaces.calculator.model.User;
import by.spaces.calculator.service.EmailAlreadyExistsException;
import by.spaces.calculator.service.UserService;
import by.spaces.calculator.service.UsernameAlreadyExistsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Authentication controller", description = "Controller with methods for user authentication")
@RestController
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService u){
        userService = u;
    }

    @Operation(summary = "Authenticate the user", description = "This method authenticates the user based on the provided login credentials.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authenticated successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    @PostMapping("/signin")
    public ResponseEntity<Void> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        userService.loginUser(loginRequest.getLogin(), loginRequest.getPassword());
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "Register the user", description = "This method registers a new user with the provided user details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseContainer> createUser(@Valid @RequestBody SignUpRequest data) {
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
                .body(new ApiResponseContainer(true,"User registered successfully"));
    }
}
