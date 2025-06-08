package com.casestudy.AmazeCare;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.casestudy.AmazeCare.Enum.UserStatus;
import com.casestudy.AmazeCare.Exception.NurseNotFoundException;
import com.casestudy.AmazeCare.Model.Nurse;
import com.casestudy.AmazeCare.Model.User;
import com.casestudy.AmazeCare.Repoitory.NurseRepository;
import com.casestudy.AmazeCare.Service.NurseService;
import com.casestudy.AmazeCare.Service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class NurseServiceTest {

    @Mock
    private NurseRepository nurseRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private NurseService nurseService;

    private Nurse nurse;
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setRole(null);

        nurse = new Nurse();
        nurse.setUser(user);
        nurse.setUserStatus(UserStatus.ACTIVE); // example status
        nurse.setName("Test Nurse");
        nurse.setEmail("test@nurse.com");
    }

    @Test
    public void testAddNurse() {
        when(userService.addUser(any(User.class))).thenReturn(user);
        when(nurseRepository.save(any(Nurse.class))).thenReturn(nurse);

        Nurse savedNurse = nurseService.AddNurse(nurse);

        assertNotNull(savedNurse);
        assertEquals("Test Nurse", savedNurse.getName());
        assertEquals(UserStatus.ACTIVE, savedNurse.getUserStatus());
        assertEquals(user, savedNurse.getUser());
        verify(userService).addUser(user);
        verify(nurseRepository).save(nurse);
    }

    @Test
    public void testEditStatus() {
        when(nurseRepository.findById(anyInt())).thenReturn(java.util.Optional.of(nurse));
        when(nurseRepository.save(any(Nurse.class))).thenReturn(nurse);

        Nurse updatedNurse = nurseService.editStatus(1, UserStatus.INACTIVE);

        assertEquals(UserStatus.INACTIVE, updatedNurse.getUserStatus());
        verify(nurseRepository).findById(1);
        verify(nurseRepository).save(nurse);
    }

    @Test
    public void testEditStatusThrowsExceptionIfNotFound() {
        when(nurseRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());

        assertThrows(NurseNotFoundException.class, () -> {
            nurseService.editStatus(1, UserStatus.INACTIVE);
        });
    }

    // Add more tests like updateNurse, getNurseByUsername similarly

}
