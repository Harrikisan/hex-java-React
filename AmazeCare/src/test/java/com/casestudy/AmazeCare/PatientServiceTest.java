package com.casestudy.AmazeCare;

import com.casestudy.AmazeCare.Enum.Role;
import com.casestudy.AmazeCare.Enum.UserStatus;
import com.casestudy.AmazeCare.Exception.PatientNotFountException;
import com.casestudy.AmazeCare.Model.Patient;
import com.casestudy.AmazeCare.Model.User;
import com.casestudy.AmazeCare.Repoitory.PatientRepository;
import com.casestudy.AmazeCare.Service.PatientService;
import com.casestudy.AmazeCare.Service.UserService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PatientServiceTest {

    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private UserService userService;

    private Patient patient;
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1);
        user.setUsername("patient01");
        user.setPassword("password123");
        user.setRole(Role.PATIENT);

        patient = new Patient();
        patient.setId(1);
        patient.setName("John Doe");
        patient.setEmail("john@example.com");
        patient.setUser(user);
        patient.setUserStatus(UserStatus.ACTIVE);
    }

    @Test
    public void testInsertPatient() {
        when(userService.addUser(user)).thenReturn(user);
        when(patientRepository.save(patient)).thenReturn(patient);

        Patient savedPatient = patientService.insertPatient(patient);
        assertEquals(patient, savedPatient);
        assertEquals(Role.PATIENT, savedPatient.getUser().getRole());
    }

    @Test
    public void testGetById() {
        when(patientRepository.getPatientById(1)).thenReturn(Optional.of(patient));
        Patient result = patientService.getById(1);
        assertEquals(patient, result);

        when(patientRepository.getPatientById(999)).thenReturn(Optional.empty());
        PatientNotFountException exception = assertThrows(PatientNotFountException.class, () ->
                patientService.getById(999));
        assertEquals("Patient not Available or Active", exception.getMessage());
    }

    @Test
    public void testEditPatientInfo() {
        Patient updated = new Patient();
        updated.setName("Jane Smith");
        updated.setEmail("jane@example.com");

        when(patientRepository.getbyUsername("patient01")).thenReturn(patient);
        when(patientRepository.save(patient)).thenReturn(patient);

        Patient result = patientService.editPatientInfo("patient01", updated);
        assertEquals("Jane Smith", result.getName());
        assertEquals("jane@example.com", result.getEmail());
    }

    @Test
    public void testEditStatus() {
        when(patientRepository.findById(1)).thenReturn(Optional.of(patient));
        when(patientRepository.save(patient)).thenReturn(patient);

        Patient updated = patientService.editStatus(UserStatus.INACTIVE, 1);
        assertEquals(UserStatus.INACTIVE, updated.getUserStatus());

        when(patientRepository.findById(999)).thenReturn(Optional.empty());
        PatientNotFountException exception = assertThrows(PatientNotFountException.class, () ->
                patientService.editStatus(UserStatus.ACTIVE, 999));
        assertEquals("Patient not Available", exception.getMessage());
    }


    @AfterEach
    public void Delete() {
        patient = null;
        user = null;
    }
}
