/*
package aqlaam.version2.service.imp;

import aqlaam.version2.dto.UserResponce;
import aqlaam.version2.exception.CustomNotFoundException;
import aqlaam.version2.model.enums.AccountType;
import aqlaam.version2.model.enums.Sex;
import aqlaam.version2.repo.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    private UserResponce createUserDto() {
        return UserResponce.builder()
                .firstName("Test")
                .lastName("Person")
                .userName("@Geru")
                .email("test@test.com")
                .password("password")
                .profilePicture("password")
                .dateOfBirth(new Date())
                .sex(Sex.MALE)
                .build();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void testGetAllUsers() {
        UserResponce userDto = createUserDto();
        userService.add(userDto);

        // Act
        List<UserResponce> result = userService.getAllUsers();

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(userDto.getFirstName(), result.get(0).getFirstName());
    }

    @Test
    void testAdd() {
        UserResponce userDto = createUserDto();

        // Act
        UserResponce result = userService.add(userDto);

        // Assert
        assertEquals(userDto.getFirstName(), result.getFirstName());
        assertEquals(userDto.getLastName(), result.getLastName());
        assertEquals(userDto.getEmail(), result.getEmail());
        assertEquals(userDto.getPassword(), result.getPassword());
        assertEquals(userDto.getDateOfBirth(), result.getDateOfBirth());
        assertEquals(userDto.getSex(), result.getSex());
    }

    @Test
    void testUpdate() {
        UserResponce userDto = createUserDto();
        UserResponce savedUser = userService.add(userDto);

        savedUser.setFirstName("UpdatedName");
        savedUser.setLastName("UpdatedLastName");
        savedUser.setEmail("updated@test.com");
        savedUser.setPassword("updatedPassword");
        savedUser.setDateOfBirth(new Date());
        savedUser.setSex(Sex.FEMALE);

        // Act
        UserResponce result = userService.update(1L, savedUser);

        // Assert
        assertEquals("UpdatedName", result.getFirstName());
        assertEquals("UpdatedLastName", result.getLastName());
        assertEquals("updated@test.com", result.getEmail());
        assertEquals("updatedPassword", result.getPassword());
        assertEquals(savedUser.getDateOfBirth(), result.getDateOfBirth());
        assertEquals(Sex.FEMALE, result.getSex());
    }

    @Test
    void testGetUserByUserName() {
        UserResponce userDto = createUserDto();
        UserResponce savedUser = userService.add(userDto);

        // Act
        UserResponce result = userService.getUserByUserName(savedUser.getUserName());

        // Assert
        assertEquals(savedUser.getUserName(), result.getUserName());
    }

    @Test
    void testGetUserByEmail() {
        UserResponce userDto = createUserDto();
        UserResponce savedUser = userService.add(userDto);

        // Act
        UserResponce result = userService.getUserByEmail(savedUser.getEmail());

        // Assert
        assertEquals(savedUser.getUserName(), result.getUserName());
    }

    @Test
    void testDeleteById() {
        UserResponce userDto = createUserDto();
        UserResponce savedUser = userService.add(userDto);

        // Act
        userService.deleteById(savedUser.getId());

        // Assert
        assertThrows(CustomNotFoundException.class, () -> userService.getUserById(savedUser.getId()));

    }

    @Test
    void getUserById() {
        UserResponce userDto = createUserDto();
        UserResponce savedUser = userService.add(userDto);

        // Act
        UserResponce result = userService.getUserById(savedUser.getId());

        // Assert
        assertEquals(savedUser.getUserName(), result.getUserName());
    }
}*/
