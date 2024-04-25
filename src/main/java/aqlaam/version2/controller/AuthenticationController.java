package aqlaam.version2.controller;

import aqlaam.version2.dto.PersonDto;
import aqlaam.version2.dto.request.PersonLoginRequest;
import aqlaam.version2.dto.request.UserRequest;
import aqlaam.version2.dto.response.UserResponse;
import aqlaam.version2.jwt.helper.JWTHelper;
import aqlaam.version2.service.interfaces.IPersonService;
import aqlaam.version2.service.interfaces.IUserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

import static aqlaam.version2.service.implemntation.UserService.logger;

/**
 * Controller for handling authentication related requests.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final IPersonService personService;
    private final IUserService userService;

    private final AuthenticationManager authenticationManager;
    private final JWTHelper jwtHelper;
    private final HttpServletResponse response;

    /**
     * Endpoint to authenticate a user.
     *
     * @param loginRequest DTO containing user's email and password.
     * @return JWT token if authentication is successful.
     */

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authenticateUser(@RequestBody PersonLoginRequest loginRequest) {
        PersonDto personDto = personService.loadUserByEmail(loginRequest.getEmail());
        logger.info("this is the account type: {}", personDto.getAccountType());
        logger.info("this is the email: {}", personDto.getEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtAccessToken = jwtHelper.generateAccessToken(personDto.getEmail(), Collections.singletonList(personDto.getAccountType().name()), personDto.getId());
        String jwtRefreshToken = jwtHelper.generateRefreshToken(loginRequest.getEmail());
        Map<String, String> tokens = jwtHelper.getTokensMap(jwtAccessToken, jwtRefreshToken);
        return new ResponseEntity<>(tokens, HttpStatus.OK);
    }

    /**
     * Endpoint to register a new user.
     *
     * @param user DTO containing new user's details.
     * @return UserResponse object if registration is successful.
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponse> addUser(@RequestBody @Valid UserRequest user) {
        UserResponse userDto = userService.add(user);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }
}