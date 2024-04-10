package aqlaam.version2.controller;

import aqlaam.version2.dto.AdminDto;
import aqlaam.version2.service.implemntation.AdminService;

import aqlaam.version2.service.interfaces.IAdminService;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final IAdminService adminService;

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @PostMapping("/add")
    public ResponseEntity<AdminDto> addAdmin(@RequestBody @Valid AdminDto admin){
        logger.info("this is the account type: {}", admin.getAccountType());
        AdminDto adminDto = adminService.add(admin);
        return new ResponseEntity<>(adminDto, HttpStatus.CREATED);
    }

    @GetMapping("/admins")
    public ResponseEntity<List<AdminDto>> getAllAdmins(){
        List<AdminDto> admins = adminService.getAllAdmins();
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    @GetMapping("/managers")
    public ResponseEntity<List<AdminDto>> getAllManagers(){
        List<AdminDto> managers = adminService.getAllManagers();
        return new ResponseEntity<>(managers, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AdminDto>> getAll(){
        List<AdminDto> admins = adminService.getAllAdmins();
        List<AdminDto> managers = adminService.getAllAdmins();
        List<AdminDto> all = new ArrayList<>();
        all.addAll(admins);
        all.addAll(managers);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminDto> getAdminById(@PathVariable Long id){
        AdminDto admin = adminService.getAdminById(id);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @GetMapping("/email")
    public ResponseEntity<AdminDto> getAdminByEmail(@RequestParam String email){
        AdminDto admin = adminService.getAdminByEmail(email);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    // update admin
    @PutMapping("/update/{id}")
    public ResponseEntity<AdminDto> updateAdmin(@PathVariable Long id, @RequestBody @Valid AdminDto admin){
        AdminDto updatedAdmin = adminService.update(id, admin);
        return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
    }






}
