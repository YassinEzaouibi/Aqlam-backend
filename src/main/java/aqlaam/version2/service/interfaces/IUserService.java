package aqlaam.version2.service.interfaces;

import aqlaam.version2.dto.request.UserRequest;
import aqlaam.version2.dto.response.UserResponse;

import java.util.List;

public interface IUserService  {

    List<UserResponse> getAllUsers();
    UserResponse add(UserRequest userDto);
    UserResponse update(Long id, UserRequest userDto);
    UserResponse getUserByUserName(String userName);
    UserResponse getUserByEmail(String email);
    void deleteById(Long id);
    UserResponse getUserById(Long id);

}


