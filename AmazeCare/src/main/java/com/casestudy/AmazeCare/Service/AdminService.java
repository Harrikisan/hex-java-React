package com.casestudy.AmazeCare.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casestudy.AmazeCare.Enum.Role;
import com.casestudy.AmazeCare.Exception.AdminNotFoundException;
import com.casestudy.AmazeCare.Model.Admin;
import com.casestudy.AmazeCare.Model.User;
import com.casestudy.AmazeCare.Repoitory.AdminRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserService userService;

    public Admin addAdmin(Admin admin) {
        User user = admin.getUser();
        user.setRole(Role.ADMIN);
        user = userService.addUser(user);
        admin.setUser(user);
        return adminRepository.save(admin);
    }

    public Admin updateAdmin(String username, Admin updatedAdmin) {
        Admin existingAdmin = adminRepository.getByUsername(username)
                .orElseThrow(() -> new AdminNotFoundException("Admin not available" ));

        // Preserve old values if not provided
        if (updatedAdmin.getName() != null && !updatedAdmin.getName().isBlank()) {
            existingAdmin.setName(updatedAdmin.getName());
        }
        if (updatedAdmin.getEmail() != null && !updatedAdmin.getEmail().isBlank()) {
            existingAdmin.setEmail(updatedAdmin.getEmail());
        }
        if (updatedAdmin.getPhone() != null && !updatedAdmin.getPhone().isBlank()) {
            existingAdmin.setPhone(updatedAdmin.getPhone());
        }
        if (updatedAdmin.getAddress() != null && !updatedAdmin.getAddress().isBlank()) {
            existingAdmin.setAddress(updatedAdmin.getAddress());
        }

        return adminRepository.save(existingAdmin);
    }
    
    public Admin getAdminByUsername(String username) {
        return adminRepository.getByUsername(username)
                .orElseThrow(() -> new AdminNotFoundException("Admin not available"));
    }

}
