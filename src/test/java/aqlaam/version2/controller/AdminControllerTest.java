package aqlaam.version2.controller;

import aqlaam.version2.dto.AdminDto;
import aqlaam.version2.service.interfaces.IAdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminControllerTest {

    @Mock
    private IAdminService adminService;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddAdmin() {
        AdminDto adminDto = mock(AdminDto.class);

        when(adminService.add(any(AdminDto.class))).thenReturn(adminDto);

        ResponseEntity<AdminDto> responseEntity = adminController.addAdmin(adminDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(adminDto, responseEntity.getBody());

        verify(adminService, times(1)).add(any(AdminDto.class));
    }

    @Test
    void testGetAllAdmins() {
        List<AdminDto> admins = new ArrayList<>();
        AdminDto admin1 = mock(AdminDto.class);
        AdminDto admin2 = mock(AdminDto.class);
        admins.add(admin1);
        admins.add(admin2);

        when(adminService.getAllAdmins()).thenReturn(admins);

        ResponseEntity<List<AdminDto>> responseEntity = adminController.getAllAdmins();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(admins, responseEntity.getBody());

        verify(adminService, times(1)).getAllAdmins();
    }

    @Test
    void testGetAllManagers() {
        List<AdminDto> managers = new ArrayList<>();
        AdminDto manager1 = mock(AdminDto.class);
        AdminDto manager2 = mock(AdminDto.class);
        managers.add(manager1);
        managers.add(manager2);

        when(adminService.getAllManagers()).thenReturn(managers);

        ResponseEntity<List<AdminDto>> responseEntity = adminController.getAllManagers();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(managers, responseEntity.getBody());

        verify(adminService, times(1)).getAllManagers();
    }

    @Test
    void testGetAll() {
        List<AdminDto> admins = new ArrayList<>();
        AdminDto admin1 = mock(AdminDto.class);
        AdminDto admin2 = mock(AdminDto.class);
        admins.add(admin1);
        admins.add(admin2);

        List<AdminDto> managers = new ArrayList<>();
        AdminDto manager1 = mock(AdminDto.class);
        AdminDto manager2 = mock(AdminDto.class);
        managers.add(manager1);
        managers.add(manager2);

        List<AdminDto> allAdminsAndManagers = new ArrayList<>();
        allAdminsAndManagers.addAll(admins);
        allAdminsAndManagers.addAll(managers);

        when(adminService.getAllAdmins()).thenReturn(admins);
        when(adminService.getAllManagers()).thenReturn(managers);

        ResponseEntity<List<AdminDto>> responseEntity = adminController.getAll();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(allAdminsAndManagers, responseEntity.getBody());

        verify(adminService, times(1)).getAllAdmins();
        verify(adminService, times(1)).getAllManagers();
    }

    @Test
    void testGetAdminById() {
        Long id = 1L;
        AdminDto adminDto = mock(AdminDto.class);

        when(adminService.getAdminById(id)).thenReturn(adminDto);

        ResponseEntity<AdminDto> responseEntity = adminController.getAdminById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(adminDto, responseEntity.getBody());

        verify(adminService, times(1)).getAdminById(id);
    }

    @Test
    void testGetAdminByEmail() {
        String email = "admin@example.com";
        AdminDto adminDto = mock(AdminDto.class);

        when(adminService.getAdminByEmail(email)).thenReturn(adminDto);

        ResponseEntity<AdminDto> responseEntity = adminController.getAdminByEmail(email);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(adminDto, responseEntity.getBody());

        verify(adminService, times(1)).getAdminByEmail(email);
    }

    @Test
    void testUpdateAdmin() {
        Long id = 1L;
        AdminDto adminDto = mock(AdminDto.class);

        when(adminService.update(id, adminDto)).thenReturn(adminDto);

        ResponseEntity<AdminDto> responseEntity = adminController.updateAdmin(id, adminDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(adminDto, responseEntity.getBody());

        verify(adminService, times(1)).update(id, adminDto);
    }
}