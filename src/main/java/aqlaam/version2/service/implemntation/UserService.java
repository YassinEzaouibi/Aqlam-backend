package aqlaam.version2.service.implemntation;

import aqlaam.version2.dto.request.UserRequest;
import aqlaam.version2.dto.response.UserResponse;
import aqlaam.version2.exception.CustomNotFoundException;
import aqlaam.version2.mapper.UserMapper;
import aqlaam.version2.model.actors.User;
import aqlaam.version2.model.enums.AccountType;
import aqlaam.version2.repo.UserRepository;
import aqlaam.version2.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public static final Logger logger = LoggerFactory.getLogger(UserService.class);
    public static final String USER_NOT_FOUND = "User not found";
    public static final String EMAIL_ALREADY_EXIST = "User already exists with same email";

    @Override
    public List<UserResponse> getAllUsers() {
        logger.info("Fetching all users");
        List<User> users = userRepository.findAllByDeletedIsFalse();
        return users.stream()
                .map(userMapper::toDto1)
                .toList();
    }

    @Override
    public UserResponse add(UserRequest userDto) {
        logger.info("Creating a new user with email: {}", userDto.getEmail());
        User userEntity = userMapper.toEntity(userDto);

        // check if user exists on the database with an existing email
        Optional<User> userEntityOptionalEmail = userRepository.findUserByEmailAndDeletedFalse(userEntity.getEmail());
        if (userEntityOptionalEmail.isPresent()) {
            throw new CustomNotFoundException(EMAIL_ALREADY_EXIST, HttpStatus.BAD_REQUEST);
        }

        // check if user exists on the database with an existing username
        Optional<User> userEntityOptionalUserName = userRepository.findByUserName(userEntity.getUserName());
        if (userEntityOptionalUserName.isPresent()) {
            throw new CustomNotFoundException("User already exists with same username", HttpStatus.BAD_REQUEST);
        }

        userEntity.setAccountType(AccountType.USER);
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User savedUserEntity = userRepository.save(userEntity);
        logger.info("User created with id: {}", savedUserEntity.getId());
        return userMapper.toDto1(savedUserEntity);
    }


    @Override
    public UserResponse update(Long id, UserRequest userDto) {
        logger.info("Updating User with id: {}", id);
        User user = userMapper.toEntity(userDto);

        Optional<User> optionalUser = userRepository.findUserByDeletedIsFalseAndId(id);
        User existingUser = optionalUser.orElseThrow(() -> {
            logger.error( USER_NOT_FOUND + " with id: {}", id);
            return new CustomNotFoundException(USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        });

        Optional<User> userOptional1 = userRepository.findUserByEmailAndDeletedFalse(user.getEmail());
        if (userOptional1.isPresent() && !userOptional1.get().getId().equals(id)) {
            throw new CustomNotFoundException(EMAIL_ALREADY_EXIST, HttpStatus.BAD_REQUEST);
        }

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        existingUser.setSex(user.getSex());
        existingUser.setDateOfBirth(user.getDateOfBirth());

        User savedUser = userRepository.save(existingUser);
        logger.info("User updated with id: {}", savedUser.getId());
        return userMapper.toDto1(savedUser);

    }

    @Override
    public UserResponse getUserByUserName(String userName) {
        logger.info("Fetching user with user name: {}", userName);
        Optional<User> optionalUser = userRepository.findByUserName(userName);
        if (optionalUser.isEmpty()) {
            logger.error("User not found with name: {}", userName);
            throw new CustomNotFoundException(USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return userMapper.toDto1(optionalUser.get());
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        logger.info("Fetching user with email: {}", email);
        Optional<User> optionalUser = userRepository.findUserByEmailAndDeletedFalse(email);
        if (optionalUser.isEmpty()) {
            logger.error("User not found with email: {}", email);
            throw new CustomNotFoundException(USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return userMapper.toDto1(optionalUser.get());
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting user with id: {}", id);
        Optional<User> optionalUser = userRepository.findUserByDeletedIsFalseAndId(id);
        if (optionalUser.isEmpty()) {
            logger.error( USER_NOT_FOUND + " with id: {}", id);
            throw new CustomNotFoundException(USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(id);
        logger.info("User deleted with id: {}", id);

    }

    @Override
    public UserResponse getUserById(Long id) {
        logger.info("Fetching user with id: {}", id);
        Optional<User> optionalUser = userRepository.findUserByDeletedIsFalseAndId(id);
        if (optionalUser.isEmpty()) {
            throw new CustomNotFoundException(USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return userMapper.toDto1(optionalUser.get());
    }


}


