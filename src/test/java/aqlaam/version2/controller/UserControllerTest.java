package aqlaam.version2.controller;

import aqlaam.version2.dto.request.UserRequest;
import aqlaam.version2.dto.response.UserResponse;
import aqlaam.version2.service.interfaces.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    private IUserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllUsers() {
        List<UserResponse> users = new ArrayList<>();
        UserResponse user1 = mock(UserResponse.class);
        UserResponse user2 = mock(UserResponse.class);
        users.add(user1);
        users.add(user2);

        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<UserResponse>> responseEntity = userController.getAllUsers();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(users, responseEntity.getBody());
    }

    @Test
    void testGetUserByUserName() {
        String userName = "username";
        UserResponse userResponse = mock(UserResponse.class);

        when(userService.getUserByUserName(userName)).thenReturn(userResponse);

        ResponseEntity<UserResponse> responseEntity = userController.getUserByUserName(userName);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userResponse, responseEntity.getBody());
    }

    @Test
    void testGetUserByEmail() {
        String email = "test@example.com";
        UserResponse userResponse = mock(UserResponse.class);

        when(userService.getUserByEmail(email)).thenReturn(userResponse);

        ResponseEntity<UserResponse> responseEntity = userController.getUserByEmail(email);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userResponse, responseEntity.getBody());
    }

    @Test
    void testGetUserById() {
        Long id = 1L;
        UserResponse userResponse = mock(UserResponse.class);

        when(userService.getUserById(id)).thenReturn(userResponse);

        ResponseEntity<UserResponse> responseEntity = userController.getUserById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userResponse, responseEntity.getBody());
    }

    @Test
    void testUpdateUser() {
        Long id = 1L;
        UserRequest userRequest = mock(UserRequest.class);
        UserResponse updatedUser = mock(UserResponse.class);

        when(userService.update(id, userRequest)).thenReturn(updatedUser);

        ResponseEntity<UserResponse> responseEntity = userController.updateUser(id, userRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedUser, responseEntity.getBody());
    }

    @Test
    void testDeleteUser() {
        Long id = 1L;

        ResponseEntity<Void> responseEntity = userController.deleteUser(id);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}
