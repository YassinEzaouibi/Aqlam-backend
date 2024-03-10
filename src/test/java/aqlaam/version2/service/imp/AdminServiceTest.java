package aqlaam.version2.service.imp;

import aqlaam.version2.dto.AdminDto;
import aqlaam.version2.mapper.AdminMapper;
import aqlaam.version2.model.enums.AccountType;
import aqlaam.version2.model.enums.Sex;
import aqlaam.version2.repo.AdminRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminServiceTest {

    @Autowired
    private  AdminRepository adminRepository;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    @BeforeEach
     void setUp() {
        adminRepository.deleteAll();
    }
    @Test
    void add() {
        AdminDto adminDto = AdminDto.builder()
                .id(1L)
                .firstName("Test")
                .lastName("User2")
                .email("eeee@ssss.com")
                .password("password")
                .dateOfBirth(new Date())
                .sex(Sex.MALE)
                .accountType(AccountType.ADMIN)
                .build();

        // Act
        AdminDto result = adminService.add(adminDto);

        // Assert
        assertEquals(adminDto.getFirstName(), result.getFirstName());
        assertEquals(adminDto.getLastName(), result.getLastName());
        assertEquals(adminDto.getEmail(), result.getEmail());
        assertEquals(adminDto.getPassword(), result.getPassword());
        assertEquals(adminDto.getDateOfBirth(), result.getDateOfBirth());
        assertEquals(adminDto.getSex(), result.getSex());
        assertEquals(adminDto.getAccountType(), result.getAccountType());

    }


    @Test
    void getAllAdmins() {
    }

    @Test
    void update() {
    }

    @Test
    void getAdminById() {
    }

    @Test
    void getAdminByEmail() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void validation() {
    }

    @AfterEach
    void tearDown() {
    }

}