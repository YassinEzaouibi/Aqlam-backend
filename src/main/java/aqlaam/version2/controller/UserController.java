package aqlaam.version2.controller;


import aqlaam.version2.service.interfaces.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import aqlaam.version2.dto.request.UserRequest;
import aqlaam.version2.dto.response.UserResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/username")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
    public ResponseEntity<UserResponse> getUserByUserName(@RequestParam String userName) {
        UserResponse user = userService.getUserByUserName(userName);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/email")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
    public ResponseEntity<UserResponse> getUserByEmail(@RequestParam String email) {
        UserResponse user = userService.getUserByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody @Valid UserRequest user) {
        UserResponse updatedUser = userService.update(id, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}