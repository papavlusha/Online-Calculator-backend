package by.spaces.calculator.controller;

import by.spaces.calculator.containers.*;
import by.spaces.calculator.model.User;
import by.spaces.calculator.service.EmailAlreadyExistsException;
import by.spaces.calculator.service.AuthUserService;
import by.spaces.calculator.service.UsernameAlreadyExistsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static by.spaces.calculator.logging.AppLogger.*;

@Tag(name = "Authentication controller", description = "Controller with methods for user authentication")
@RestController
public class AuthController {
    private final AuthUserService userService;

    @Autowired
    public AuthController(AuthUserService u){
        userService = u;
    }

    @Operation(summary = "Authenticate the user", description = "This method authenticates the user based on the provided login credentials.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authenticated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtAuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Authentication failed",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = "{\"success\": false, \"message\": \"User authorization result\"}")))
    })
    @PostMapping("/signin")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try{
            String token = userService.loginUser(loginRequest.getLogin(), loginRequest.getPassword());
            JwtAuthenticationResponse response = new JwtAuthenticationResponse(token);
            logInfo(AuthController.class, loginRequest.getLogin() + " authorization succeeded");
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            ApiResponseContainer errorResponse = new ApiResponseContainer(false, "User authorization failed");
            logError(AuthController.class, errorResponse.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }


    @Operation(summary = "Register the user", description = "This method registers a new user with the provided user details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "User registration failed",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = "{\"success\": false, \"message\": \"User authorization result\"}")))
    })
    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseContainer> createUser(@Valid @RequestBody SignUpRequest data) {
        User user = User
                .builder()
                .login(data.getLogin())
                .email(data.getEmail())
                .userPassword(data.getPassword())
                .username(data.getUsername())
                .build();

        try {
            userService.registerUser(user);
            ApiResponseContainer response = new ApiResponseContainer(true, "Registered user with login: " + data.getLogin());
            logInfo(AuthController.class, response.getMessage());
            return ResponseEntity.ok().body(response);
        } catch (UsernameAlreadyExistsException | EmailAlreadyExistsException e) {
            ApiResponseContainer errorResponse = new ApiResponseContainer(false, e.getMessage());
            logWarn(AuthController.class, errorResponse.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
