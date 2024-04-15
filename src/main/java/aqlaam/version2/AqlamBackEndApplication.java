package aqlaam.version2;

import aqlaam.version2.dto.AdminDto;
import aqlaam.version2.model.enums.AccountType;
import aqlaam.version2.model.enums.Sex;
import aqlaam.version2.service.interfaces.IAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;

import static aqlaam.version2.service.implemntation.UserService.logger;

@SpringBootApplication
@RequiredArgsConstructor
public class AqlamBackEndApplication implements CommandLineRunner {

    private final IAdminService adminService;

    public static void main(String[] args) {
        SpringApplication.run(AqlamBackEndApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Hello World");
        if (adminService.getAllManagers().isEmpty()) {
            AdminDto adminDto = AdminDto.builder()
                    .firstName("Yassin")
                    .lastName("Ezaouibi")
                    .email("yassin@mail.com")
                    .password("Azaid2@12")
                    .dateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01"))
                    .sex(Sex.MALE)
                    .accountType(AccountType.MANAGER)
                    .build();
            adminService.add(adminDto);
        }
        logger.info("Admin data: {}", adminService.getAdminByEmail("yassin@mail.com"));
    }
}