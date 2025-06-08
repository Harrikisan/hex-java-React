package com.casestudy.AmazeCare.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.casestudy.AmazeCare.Model.Admin;
import com.casestudy.AmazeCare.Service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/add")
    public ResponseEntity<?> addAdmin(@RequestBody Admin admin) {
        return ResponseEntity.ok(adminService.addAdmin(admin));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAdmin(@RequestBody Admin admin, Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(adminService.updateAdmin(username, admin));
    }
    
    @GetMapping("/get")
    public ResponseEntity<?> getAdminProfile(Principal principal) {
        String username = principal.getName();
        Admin admin = adminService.getAdminByUsername(username);
        return ResponseEntity.ok(admin);
    }
}
