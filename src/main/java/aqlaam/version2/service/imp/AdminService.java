package aqlaam.version2.service.imp;

import aqlaam.version2.dto.AdminDto;
import aqlaam.version2.exception.CustomNotFoundException;
import aqlaam.version2.mapper.AdminMapper;
import aqlaam.version2.model.actors.Admin;
import aqlaam.version2.repo.AdminRepository;
import aqlaam.version2.service.IAdminٍService;
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
import java.util.stream.Collectors;


@Service
public class AdminService implements IAdminٍService {


    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);


    @Autowired
    public AdminService(
            AdminRepository adminRepository,
            AdminMapper adminMapper
    ) {
        this.adminRepository = adminRepository;
        this.adminMapper = adminMapper;
    }


    @Override
    public List<AdminDto> getAllAdmins() {
        return adminRepository.findAll()
                .stream()
                .map(adminMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AdminDto add(AdminDto adminDto) {
        validation(adminDto);
        logger.info("Adding a new admin with email: {}", adminDto.getEmail());
        Optional<Admin> adminOptional = adminRepository.findByEmail(adminDto.getEmail());
        if(adminOptional.isPresent()){
            logger.error("Admin already exists with same email"+ adminDto.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Admin already exists with same email"+ adminDto.getEmail());
        }
        Admin admin = adminMapper.dtoToEntity(adminDto);
        Admin savedAdmin = adminRepository.save(admin);
        logger.info("Admin added with id: {}", savedAdmin.getId());
        return adminMapper.entityToDto(savedAdmin);
    }

    @Override
    public AdminDto update(Long id, AdminDto adminDto) {
        validation(adminDto);
        logger.info("Updating Admin with id: {}", id);
        Admin admin = adminMapper.dtoToEntity(adminDto);
        Optional<Admin> optionalAdmin = adminRepository.findAdminByDeletedIsFalseAndId(id);

        Admin existingAdmin = optionalAdmin.orElseThrow(() -> {
            logger.error("Admin not found with id: {}", id);
            return new CustomNotFoundException("Admin not found", HttpStatus.NOT_FOUND);
        });

        Optional<Admin> adminOptional1 = adminRepository.findByEmail(
                admin.getEmail());
        if(adminOptional1.isPresent()){
            logger.error("Admin already exists with same email");
            throw new CustomNotFoundException("Admin already exists with same email", HttpStatus.BAD_REQUEST);
        }
        existingAdmin.setEmail(admin.getEmail());
        existingAdmin.setFirstName(admin.getFirstName());
        existingAdmin.setLastName(admin.getLastName());
        existingAdmin.setPassword(admin.getPassword());
        existingAdmin.setSex(admin.getSex());
        existingAdmin.setDateOfBirth(admin.getDateOfBirth());

        Admin savedAdmin = adminRepository.save(existingAdmin);
        logger.info("Admin updated with id: {}", savedAdmin.getId());
        return adminMapper.entityToDto(savedAdmin);
    }

    @Override
    public AdminDto getAdminById(Long id) {
        logger.info("Fetching admin with id: {}", id);
        Optional<Admin> optionalAdmin = adminRepository.findAdminByDeletedIsFalseAndId(id);
        if(optionalAdmin.isEmpty()){
            logger.error("Admin not found with id: {}", id);
            throw new CustomNotFoundException("Admin not found", HttpStatus.NOT_FOUND);
        }
        return adminMapper.entityToDto(optionalAdmin.get());
    }

    @Override
    public AdminDto getAdminByEmail(String email) {
        logger.info("Fetching admin with email: {}", email);
        Optional<Admin> optionalAdmin = adminRepository.findByEmail(email);
        if(optionalAdmin.isEmpty()){
            logger.error("Admin not found with email: {}", email);
            throw new CustomNotFoundException("Admin not found", HttpStatus.NOT_FOUND);
        }
        return adminMapper.entityToDto(optionalAdmin.get());
    }


    @Override
    public void deleteById(Long id) {
        logger.info("Deleting admin with id: {}", id);
        Optional<Admin> optionalAdmin = adminRepository.findAdminByDeletedIsFalseAndId(id);
        if(optionalAdmin.isEmpty()){
            logger.error("Admin not found with id: {}", id);
            throw new CustomNotFoundException("Admin not found", HttpStatus.NOT_FOUND);
        }
        adminRepository.deleteById(id);
        logger.info("Admin deleted with id: {}", id);
    }


 @Override
 public void validation(AdminDto adminDto) {
     if (adminDto == null) {
         throw new ValidationException("Admin data is required.");
     }

     if (StringUtils.isBlank(adminDto.getFirstName())) {
         throw new ValidationException("First name is required.");
     }

     if (StringUtils.isBlank(adminDto.getLastName())) {
         throw new ValidationException("Last name is required.");
     }

     if (adminDto.getSex() == null) {
         throw new ValidationException("Sex is required.");
     }

     if (adminDto.getDateOfBirth() == null) {
         throw new ValidationException("Date of birth is required yyyy-MM-dd.");
     }

     if (StringUtils.isBlank(adminDto.getEmail())) {
         throw new ValidationException("Email is required.");
     }

     if (StringUtils.isBlank(adminDto.getPassword())) {
         throw new ValidationException("Password is required.");
     }

     if (StringUtils.isBlank(String.valueOf(adminDto.getAccountType()))) {
         throw new ValidationException("Account Type is required.");
     }

    }
}


