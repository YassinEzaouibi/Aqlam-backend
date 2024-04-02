package aqlaam.version2.service.interfaces;

import aqlaam.version2.dto.UserRequest;
import aqlaam.version2.dto.UserResponce;

import java.util.List;

public interface IUserService  {

    List<UserResponce> getAllUsers();
    UserResponce add(UserRequest userDto);
    UserResponce update(Long id, UserRequest userDto);
    UserResponce getUserByUserName(String userName);
    UserResponce getUserByEmail(String email);
    void deleteById(Long id);
    UserResponce getUserById(Long id);

}


