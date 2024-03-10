package aqlaam.version2.service.imp;

import aqlaam.version2.dto.UserDto;
import aqlaam.version2.exception.CustomNotFoundException;
import aqlaam.version2.mapper.UserMapper;
import aqlaam.version2.model.actors.User;
import aqlaam.version2.repo.UserRepository;
import aqlaam.version2.service.IUserService;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    public UserService(
            UserRepository userRepository,
            UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @Override
    public List<UserDto> getAllUsers() {
        return null;
    }

    @Override
    public UserDto add(UserDto userDto) {
        validation(userDto);
        logger.info("Adding a new user with email: {}", userDto.getEmail());
        Optional<User> userOptional = userRepository.findByEmail(userDto.getEmail());
        if(userOptional.isPresent()){
            logger.error("User already exists with same email"+ userDto.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists with same email"+ userDto.getEmail());
        }
        User user = userMapper.dtoToEntity(userDto);
        User savedUser = userRepository.save(user);
        logger.info("User added with id: {}", savedUser.getId());
        return userMapper.entityToDto(savedUser);
    }
    @Override
    public UserDto update(Long id, UserDto userDto) {
        validation(userDto);
        logger.info("Updating User with id: {}", id);
        User user = userMapper.dtoToEntity(userDto);
        Optional<User> optionalUser = userRepository.findUserByDeletedIsFalseAndId(id);

        User existingUser = optionalUser.orElseThrow(() -> {
            logger.error("User not found with id: {}", id);
            return new CustomNotFoundException("User not found", HttpStatus.NOT_FOUND);
        });

        Optional<User> userOptional1 = userRepository.findByEmail(
                user.getEmail());
        if(userOptional1.isPresent()){
            logger.error("User already exists with same email");
            throw new CustomNotFoundException("User already exists with same email", HttpStatus.BAD_REQUEST);
        }
        existingUser.setEmail(user.getEmail());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setPassword(user.getPassword());
        existingUser.setSex(user.getSex());
        existingUser.setDateOfBirth(user.getDateOfBirth());

        User savedUser = userRepository.save(existingUser);
        logger.info("User updated with id: {}", savedUser.getId());
        return userMapper.entityToDto(savedUser);

    }

    @Override
    public UserDto getUserByUserName(String userName) {
        logger.info("Fetching user with user name: {}", userName);
        Optional<User> optionalUser = userRepository.findByUserName(userName);
        if (optionalUser.isEmpty()) {
            logger.error("User not found with name: {}", userName);
            throw new CustomNotFoundException("User not found", HttpStatus.NOT_FOUND);
        }
        return userMapper.entityToDto(optionalUser.get());
    }

    @Override
    public UserDto getUserByEmail(String email) {
        logger.info("Fetching user with email: {}", email);
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            logger.error("User not found with email: {}", email);
            throw new CustomNotFoundException("User not found", HttpStatus.NOT_FOUND);
        }
        return userMapper.entityToDto(optionalUser.get());
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting user with id: {}", id);
        Optional<User> optionalUser = userRepository.findUserByDeletedIsFalseAndId(id);
        if(optionalUser.isEmpty()){
            logger.error("User not found with id: {}", id);
            throw new CustomNotFoundException("User not found", HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(id);
        logger.info("User deleted with id: {}", id);

    }

    @Override
    public void validation(UserDto userDto) {
        if (userDto == null) {
            throw new ValidationException("User data is required.");
        }

        if (StringUtils.isBlank(userDto.getFirstName())) {
            throw new ValidationException("First name is required.");
        }

        if (StringUtils.isBlank(userDto.getLastName())) {
            throw new ValidationException("Last name is required.");
        }

        if (userDto.getSex() == null) {
            throw new ValidationException("Sex is required.");
        }

        if (userDto.getDateOfBirth() == null) {
            throw new ValidationException("Date of birth is required.");
        }

        if (StringUtils.isBlank(userDto.getEmail())) {
            throw new ValidationException("Email is required.");
        }

        if (StringUtils.isBlank(userDto.getPassword())) {
            throw new ValidationException("Password is required.");
        }

        if (userDto.getAccountType() == null) {
            throw new ValidationException("Account type is required.");
        }

        if (StringUtils.isBlank(userDto.getProfilePicture())) {
            throw new ValidationException("Profile picture is required.");
        }

        if (userDto.getBookCollectionIds() == null || userDto.getBookCollectionIds().isEmpty()) {
            throw new ValidationException("At least one book collection id is required.");
        }

    }
}


