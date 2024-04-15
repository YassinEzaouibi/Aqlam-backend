package aqlaam.version2.service.implemntation;

import aqlaam.version2.dto.request.UserRequest;
import aqlaam.version2.dto.response.UserResponse;
import aqlaam.version2.exception.CustomNotFoundException;
import aqlaam.version2.mapper.UserMapper;
import aqlaam.version2.model.actors.User;
import aqlaam.version2.model.enums.Sex;
import aqlaam.version2.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userMapper = mock(UserMapper.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserService(userRepository, userMapper, passwordEncoder);
    }

    @Test
    void shouldAddUserWhenEmailAndUsernameAreUnique() {
        UserRequest userDto = UserRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password")
                .dateOfBirth(new Date())
                .sex(Sex.MALE)
                .userName("john.doe")
                .build();
        User user = new User();
        UserResponse userResponse = UserResponse.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .userName("john.doe")
                .build();
        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(userRepository.findUserByEmailAndDeletedFalse(any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn("hashedPassword");
        when(userRepository.save(any())).thenReturn(user);
        when(userMapper.toDto1(user)).thenReturn(userResponse);
        UserResponse addedUser = userService.add(userDto);
        assertNotNull(addedUser);
        assertEquals(userResponse, addedUser);
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        UserRequest userDto = UserRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password")
                .dateOfBirth(new Date())
                .sex(Sex.MALE)
                .userName("john.doe")
                .build();
        User user = new User();
        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(userRepository.findUserByEmailAndDeletedFalse(any())).thenReturn(Optional.of(user));
        assertThrows(CustomNotFoundException.class, () -> userService.add(userDto));
    }

    @Test
    void shouldUpdateUserWhenEmailIsUnique() {
        Long id = 1L;
        UserRequest userDto = UserRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password")
                .dateOfBirth(new Date())
                .sex(Sex.MALE)
                .userName("john.doe")
                .build();
        User user = new User();
        UserResponse userResponse = UserResponse.builder()
                .id(id)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .userName("john.doe")
                .build();
        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(userRepository.findUserByDeletedIsFalseAndId(id)).thenReturn(Optional.of(user));
        when(userRepository.findUserByEmailAndDeletedFalse(any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn("hashedPassword");
        when(userRepository.save(any())).thenReturn(user);
        when(userMapper.toDto1(user)).thenReturn(userResponse);
        UserResponse updatedUser = userService.update(id, userDto);
        assertNotNull(updatedUser);
        assertEquals(userResponse, updatedUser);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingUser() {
        Long id = 1L;
        UserRequest userDto = UserRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password")
                .dateOfBirth(new Date())
                .sex(Sex.MALE)
                .userName("john.doe")
                .build();
        User user = new User();
        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(userRepository.findUserByDeletedIsFalseAndId(id)).thenReturn(Optional.empty());
        assertThrows(CustomNotFoundException.class, () -> userService.update(id, userDto));
    }

    @Test
    void shouldDeleteUserWhenExists() {
        Long id = 1L;
        User user = new User();
        when(userRepository.findUserByDeletedIsFalseAndId(id)).thenReturn(Optional.of(user));
        assertDoesNotThrow(() -> userService.deleteById(id));
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingUser() {
        Long id = 1L;
        when(userRepository.findUserByDeletedIsFalseAndId(id)).thenReturn(Optional.empty());
        assertThrows(CustomNotFoundException.class, () -> userService.deleteById(id));
    }
}