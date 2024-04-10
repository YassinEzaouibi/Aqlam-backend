package aqlaam.version2.controller;


import aqlaam.version2.service.interfaces.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import aqlaam.version2.dto.request.UserRequest;
import aqlaam.version2.dto.response.UserResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping("/add")
    public ResponseEntity<UserResponse> addUser(@RequestBody @Valid UserRequest user) {
        UserResponse userDto = userService.add(user);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/username")
    public ResponseEntity<UserResponse> getUserByUserName(@RequestParam String userName) {
        UserResponse user = userService.getUserByUserName(userName);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/email")
    public ResponseEntity<UserResponse> getUserByEmail(@RequestParam String email) {
        UserResponse user = userService.getUserByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody @Valid UserRequest user) {
        UserResponse updatedUser = userService.update(id, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}