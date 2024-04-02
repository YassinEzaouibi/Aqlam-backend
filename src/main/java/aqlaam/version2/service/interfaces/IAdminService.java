package aqlaam.version2.service.interfaces;

import aqlaam.version2.dto.AdminDto;

import java.util.List;

public interface IAdminService {

    List<AdminDto> getAllAdmins();
    List<AdminDto> getAllManagers();
    AdminDto add(AdminDto adminDto);
    AdminDto update(Long id, AdminDto adminDto);
    AdminDto getAdminByEmail(String email);
    void deleteById(Long id);
    AdminDto getAdminById(Long id);
}
