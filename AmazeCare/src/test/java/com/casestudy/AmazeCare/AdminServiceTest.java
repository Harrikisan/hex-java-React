package com.casestudy.AmazeCare;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private AdminService adminService;

    private Admin admin;
    private User user;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUsername("admin1");
        user.setPassword("secret");
        user.setRole(Role.ADMIN);

        admin = new Admin();
        admin.setId(1);
        admin.setName("Admin Name");
        admin.setEmail("admin@example.com");
        admin.setPhone("1234567890");
        admin.setAddress("Admin Street");
        admin.setUser(user);
    }

    @Test
    public void testAddAdmin_Success() {
        when(userService.addUser(any(User.class))).thenReturn(user);
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        Admin result = adminService.addAdmin(admin);

        assertNotNull(result);
        assertEquals("Admin Name", result.getName());
        assertEquals(Role.ADMIN, result.getUser().getRole());
        verify(userService).addUser(any(User.class));
        verify(adminRepository).save(any(Admin.class));
    }

    @Test
    public void testUpdateAdmin_Success() {
        Admin updatedAdmin = new Admin();
        updatedAdmin.setName("Updated Name");
        updatedAdmin.setEmail("updated@example.com");

        when(adminRepository.getByUsername("admin1")).thenReturn(Optional.of(admin));
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        Admin result = adminService.updateAdmin("admin1", updatedAdmin);

        assertEquals("Updated Name", result.getName());
        assertEquals("updated@example.com", result.getEmail());
        verify(adminRepository).getByUsername("admin1");
        verify(adminRepository).save(admin);
    }

    @Test
    public void testUpdateAdmin_AdminNotFound() {
        when(adminRepository.getByUsername("invalid_user")).thenReturn(Optional.empty());

        Admin updatedAdmin = new Admin();
        updatedAdmin.setName("Updated");

        assertThrows(AdminNotFoundException.class, () ->
                adminService.updateAdmin("invalid_user", updatedAdmin));
    }

    @Test
    public void testGetAdminByUsername_Success() {
        when(adminRepository.getByUsername("admin1")).thenReturn(Optional.of(admin));

        Admin result = adminService.getAdminByUsername("admin1");

        assertNotNull(result);
        assertEquals("Admin Name", result.getName());
        verify(adminRepository).getByUsername("admin1");
    }

    @Test
    public void testGetAdminByUsername_AdminNotFound() {
        when(adminRepository.getByUsername("unknown")).thenReturn(Optional.empty());

        assertThrows(AdminNotFoundException.class, () ->
                adminService.getAdminByUsername("unknown"));
    }
}
