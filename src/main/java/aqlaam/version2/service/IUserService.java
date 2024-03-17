package aqlaam.version2.service;

import aqlaam.version2.dto.UserDto;

import java.util.List;

public interface IUserService  {

    List<UserDto> getAllUsers();
    UserDto add(UserDto userDto);
    UserDto update(Long id, UserDto userDto);
    UserDto getUserByUserName(String userName);
    UserDto getUserByEmail(String email);
    void deleteById(Long id);
    UserDto getUserById(Long id);

}


