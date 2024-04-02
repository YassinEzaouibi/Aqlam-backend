package aqlaam.version2.service.implemntation;

import aqlaam.version2.dto.AdminDto;
import aqlaam.version2.exception.CustomNotFoundException;
import aqlaam.version2.mapper.AdminMapper;
import aqlaam.version2.model.actors.Admin;
import aqlaam.version2.model.enums.AccountType;
import aqlaam.version2.repo.AdminRepository;
import aqlaam.version2.service.interfaces.IAdminService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService implements IAdminService {

    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Override
    public List<AdminDto> getAllAdmins() {
        logger.info("Fetching all admins");
        List<Admin> admins = adminRepository.findAllByAccountTypeAndDeletedFalse(AccountType.ADMIN);
        return admins.stream()
                .map(adminMapper::toDto)
                .toList();
    }

    @Override
    public List<AdminDto> getAllManagers() {
        logger.info("Fetching all managers");
        List<Admin> managers = adminRepository.findAllByAccountTypeAndDeletedFalse(AccountType.MANAGER);
        return managers.stream()
                .map(adminMapper::toDto)
                .toList();
    }

    @Override
    public AdminDto add(AdminDto adminDto) {
        logger.info("Creating a new admin with email: {}", adminDto.getEmail());
        Admin admin = adminMapper.toEntity(adminDto);
        Optional<Admin> adminOptional = adminRepository.findAdminByEmail(adminDto.getEmail());
        if (adminOptional.isPresent()){
            throw new CustomNotFoundException("Admin already existe with this email", HttpStatus.BAD_REQUEST);
        }
        Admin savedAdmin = adminRepository.save(admin);
        return adminMapper.toDto(savedAdmin);
    }

    @Override
    public AdminDto update(Long id, AdminDto adminDto) {

        Admin admin = adminMapper.toEntity(adminDto);

        Optional<Admin> optionalAdmin = adminRepository.findAdminByIdAndDeletedFalse(id);
        Admin exestingAdmin = optionalAdmin.orElseThrow(() -> new CustomNotFoundException("Admin not found", HttpStatus.NOT_FOUND));

        Optional<Admin> optionalAdmin2 = adminRepository.findAdminByEmailAndDeletedFalse(admin.getEmail());
        if (optionalAdmin2.isPresent()){
            throw new CustomNotFoundException("Admin already existe with this email", HttpStatus.BAD_REQUEST);
        }

        exestingAdmin.setFirstName(admin.getFirstName());
        exestingAdmin.setLastName(admin.getLastName());
        exestingAdmin.setEmail(admin.getEmail());
        exestingAdmin.setPassword(admin.getPassword());
        exestingAdmin.setDateOfBirth(admin.getDateOfBirth());
        exestingAdmin.setAccountType(admin.getAccountType());

        Admin savedAdmin = adminRepository.save(exestingAdmin);
        return adminMapper.toDto(savedAdmin);
    }

    @Override
    public AdminDto getAdminByEmail(String email) {
        logger.info("Fetching admin with email: {}", email);
        Optional<Admin> optionalAdmin = adminRepository.findAdminByEmail(email);
        if (optionalAdmin.isEmpty()) {
            logger.error("Admin not found with email: {}", email);
            throw new CustomNotFoundException("Admin not found", HttpStatus.NOT_FOUND);
        }
        return adminMapper.toDto(optionalAdmin.get());
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting admin with id: {}", id);
        Optional<Admin> optionalAdmin = adminRepository.findAdminByIdAndDeletedFalse(id);
        if (optionalAdmin.isEmpty()) {
            logger.error("Admin not found with id: {}", id);
            throw new CustomNotFoundException("Admin not found", HttpStatus.NOT_FOUND);
        }
        adminRepository.deleteById(id);
        logger.info("Admin deleted with id: {}", id);
    }

    @Override
    public AdminDto getAdminById(Long id) {
        logger.info("Getting admin with id: {}", id);
        Optional<Admin> optionalAdmin = adminRepository.findAdminByIdAndDeletedFalse(id);
        if(optionalAdmin.isEmpty()){
            throw new CustomNotFoundException("Admin not found", HttpStatus.NOT_FOUND);
        }
        return adminMapper.toDto(optionalAdmin.get());
    }
}
