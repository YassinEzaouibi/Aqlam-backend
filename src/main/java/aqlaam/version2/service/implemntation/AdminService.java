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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService implements IAdminService {

    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);
    private static final String ADMIN_NOT_FOUND = "Admin not found";


    @Override
    public List<AdminDto> getAllAdmins() {
        logger.info("Fetching all admins");
        List<Admin> admins = adminRepository.findAllByAccountTypeAndDeletedFalse(AccountType.ADMIN);
        return admins.stream()
                .map(adminMapper::toDto)
                .toList();
    }

    @Override
    public List<AdminDto> getAll() {
        logger.info("Fetching all super roles admin and managers");
        List<Admin> admins = adminRepository.findAll();
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
        admin.setPassword(passwordEncoder.encode(adminDto.getPassword()));
        Admin savedAdmin = adminRepository.save(admin);
        return adminMapper.toDto(savedAdmin);
    }

    @Override
    public AdminDto update(Long id, AdminDto adminDto) {
        Admin admin = adminMapper.toEntity(adminDto);

        Optional<Admin> optionalAdmin = adminRepository.findAdminByIdAndDeletedFalse(id);
        Admin existingAdmin = optionalAdmin.orElseThrow(() -> new CustomNotFoundException(ADMIN_NOT_FOUND, HttpStatus.NOT_FOUND));

        Optional<Admin> optionalAdminWithEmail = adminRepository.findAdminByEmailAndDeletedFalse(admin.getEmail());
        if (optionalAdminWithEmail.isPresent() && !optionalAdminWithEmail.get().getId().equals(id)){
            throw new CustomNotFoundException("Admin already exist with this email", HttpStatus.BAD_REQUEST);
        }

        existingAdmin.setFirstName(admin.getFirstName());
        existingAdmin.setLastName(admin.getLastName());
        existingAdmin.setPassword(passwordEncoder.encode(admin.getPassword()));
        existingAdmin.setDateOfBirth(admin.getDateOfBirth());
        existingAdmin.setAccountType(admin.getAccountType());

        Admin savedAdmin = adminRepository.save(existingAdmin);
        return adminMapper.toDto(savedAdmin);
    }

    @Override
    public AdminDto getAdminByEmail(String email) {
        logger.info("Fetching admin with email: {}", email);
        Optional<Admin> optionalAdmin = adminRepository.findAdminByEmail(email);
        if (optionalAdmin.isEmpty()) {
            logger.error("Admin not found with email: {}", email);
            throw new CustomNotFoundException(ADMIN_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return adminMapper.toDto(optionalAdmin.get());
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting admin with id: {}", id);
        Optional<Admin> optionalAdmin = adminRepository.findAdminByIdAndDeletedFalse(id);
        if (optionalAdmin.isEmpty()) {
            logger.error("Admin not found with id: {}", id);
            throw new CustomNotFoundException(ADMIN_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        adminRepository.deleteById(id);
        logger.info("Admin deleted with id: {}", id);
    }

    @Override
    public AdminDto getAdminById(Long id) {
        logger.info("Getting admin with id: {}", id);
        Optional<Admin> optionalAdmin = adminRepository.findAdminByIdAndDeletedFalse(id);
        if(optionalAdmin.isEmpty()){
            throw new CustomNotFoundException(ADMIN_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return adminMapper.toDto(optionalAdmin.get());
    }
}
