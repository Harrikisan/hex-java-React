package com.casestudy.AmazeCare;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.casestudy.AmazeCare.Enum.Role;
import com.casestudy.AmazeCare.Exception.AdminNotFoundException;
import com.casestudy.AmazeCare.Model.Admin;
import com.casestudy.AmazeCare.Model.User;
import com.casestudy.AmazeCare.Repoitory.AdminRepository;
import com.casestudy.AmazeCare.Service.AdminService;
import com.casestudy.AmazeCare.Service.UserService;

@SpringBootTest
public class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private UserService userService;

    private Admin admin;
    private User user;

    @BeforeEach
    public void init() {
        user = new User();
        user.setId(1);
        user.setUsername("mainAdmin");
        user.setPassword("mainPass");
        user.setRole(Role.ADMIN);

        admin = new Admin();
        admin.setId(1);
        admin.setName("Main Admin");
        admin.setEmail("mainadmin@example.com");
        admin.setPhone("9999999999");
        admin.setAddress("Main Admin Street");
        admin.setUser(user);
    }

    @Test
    public void testAddAdmin() {
        when(userService.addUser(user)).thenReturn(user);
        when(adminRepository.save(admin)).thenReturn(admin);

        Admin savedAdmin = adminService.addAdmin(admin);
        assertEquals(admin, savedAdmin);
    }

    @Test
    public void testUpdateAdmin() {
        String username = "mainAdmin";
        Admin adminToUpdate = new Admin();
        adminToUpdate.setName("New Admin");
        adminToUpdate.setEmail("newadmin@example.com");
        adminToUpdate.setPhone("8888888888");
        adminToUpdate.setAddress("New Admin Road");

        when(adminRepository.getByUsername(username)).thenReturn(Optional.of(admin));
        when(adminRepository.save(admin)).thenReturn(admin);

        Admin updated = adminService.updateAdmin(username, adminToUpdate);
        assertEquals("New Admin", updated.getName());
        assertEquals("newadmin@example.com", updated.getEmail());
        assertEquals("8888888888", updated.getPhone());
        assertEquals("New Admin Road", updated.getAddress());

        AdminNotFoundException ex = assertThrows(AdminNotFoundException.class,
                () -> adminService.updateAdmin("wrongAdmin", adminToUpdate));
        assertEquals("Admin not available", ex.getMessage());
    }

    @Test
    public void testGetAdminByUsername() {
        when(adminRepository.getByUsername("mainAdmin")).thenReturn(Optional.of(admin));
        Admin found = adminService.getAdminByUsername("mainAdmin");
        assertEquals(admin, found);

        AdminNotFoundException ex = assertThrows(AdminNotFoundException.class,
                () -> adminService.getAdminByUsername("unknownUser"));
        assertEquals("Admin not available", ex.getMessage());
    }

    @AfterEach
    public void Delete() {
        admin = null;
        user = null;
    }
}
