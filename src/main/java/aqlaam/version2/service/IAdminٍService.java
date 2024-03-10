package aqlaam.version2.service;

import aqlaam.version2.dto.AdminDto;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface IAdminٍService {

    List<AdminDto> getAllAdmins();
    AdminDto add(AdminDto AdminDto);
    AdminDto update(Long id, AdminDto AdminDto);
    AdminDto getAdminById(Long id);
    AdminDto getAdminByEmail(String email);
    void deleteById(Long id);
/*
    void checkExistEmail(AdminDto adminDto);
*/
    void validation(AdminDto adminDto);


}


