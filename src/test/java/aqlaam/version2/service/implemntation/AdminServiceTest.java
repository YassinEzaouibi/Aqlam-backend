package aqlaam.version2.service.implemntation;

import aqlaam.version2.dto.AdminDto;
import aqlaam.version2.exception.CustomNotFoundException;
import aqlaam.version2.mapper.AdminMapper;
import aqlaam.version2.model.actors.Admin;
import aqlaam.version2.model.enums.AccountType;
import aqlaam.version2.model.enums.Sex;
import aqlaam.version2.repo.AdminRepository;
import aqlaam.version2.service.implemntation.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AdminServiceTest {

    private AdminRepository adminRepository;
    private AdminMapper adminMapper;
    private PasswordEncoder passwordEncoder;
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        adminRepository = mock(AdminRepository.class);
        adminMapper = mock(AdminMapper.class);
        passwordEncoder = mock(PasswordEncoder.class);
        adminService = new AdminService(adminRepository, adminMapper, passwordEncoder);
    }

    @Test
    void testGetAllAdmins() {
        // Arrange
        List<Admin> admins = new ArrayList<>();
        Admin admin = new Admin();
        admins.add(admin);
        when(adminRepository.findAllByAccountTypeAndDeletedFalse(AccountType.ADMIN)).thenReturn(admins);

        // Act
        List<AdminDto> adminDtos = adminService.getAllAdmins();

        // Assert
        assertEquals(1, adminDtos.size());
    }

    @Test
    void testAddAdmin() {
        // Arrange
        AdminDto adminDto = AdminDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password")
                .dateOfBirth(new Date())
                .sex(Sex.MALE)
                .accountType(AccountType.ADMIN)
                .build();
        Admin admin = new Admin();
        when(adminMapper.toEntity(adminDto)).thenReturn(admin);
        when(adminRepository.findAdminByEmail(any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn("hashedPassword");
        when(adminRepository.save(any())).thenReturn(admin);
        when(adminMapper.toDto(admin)).thenReturn(adminDto);

        // Act
        AdminDto savedAdminDto = adminService.add(adminDto);

        // Assert
        assertNotNull(savedAdminDto);
        assertEquals(adminDto, savedAdminDto);
    }

    @Test
    void testUpdateAdmin() {
        // Arrange
        Long id = 1L;
        AdminDto adminDto = AdminDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password")
                .dateOfBirth(new Date())
                .sex(Sex.MALE)
                .accountType(AccountType.ADMIN)
                .build();
        Admin admin = new Admin();
        when(adminMapper.toEntity(adminDto)).thenReturn(admin);
        when(adminRepository.findAdminByIdAndDeletedFalse(id)).thenReturn(Optional.of(admin));
        when(adminRepository.findAdminByEmailAndDeletedFalse(any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn("hashedPassword");
        when(adminRepository.save(any())).thenReturn(admin);
        when(adminMapper.toDto(admin)).thenReturn(adminDto);

        // Act
        AdminDto updatedAdminDto = adminService.update(id, adminDto);

        // Assert
        assertNotNull(updatedAdminDto);
        assertEquals(adminDto, updatedAdminDto);
    }

    @Test
    void testGetAdminByEmail() {
        // Arrange
        String email = "test@example.com";
        Admin admin = new Admin();
        AdminDto adminDto = AdminDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email(email)
                .password("password")
                .dateOfBirth(new Date())
                .sex(Sex.MALE)
                .accountType(AccountType.ADMIN)
                .build();
        when(adminRepository.findAdminByEmail(email)).thenReturn(Optional.of(admin));
        when(adminMapper.toDto(admin)).thenReturn(adminDto);

        // Act
        AdminDto foundAdminDto = adminService.getAdminByEmail(email);

        // Assert
        assertNotNull(foundAdminDto);
    }

    @Test
    void testDeleteById() {
        // Arrange
        Long id = 1L;
        Admin admin = new Admin();
        when(adminRepository.findAdminByIdAndDeletedFalse(id)).thenReturn(Optional.of(admin));

        // Act
        assertDoesNotThrow(() -> adminService.deleteById(id));

        // Assert
        verify(adminRepository, times(1)).deleteById(id);
    }
}
