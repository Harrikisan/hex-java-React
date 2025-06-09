package com.hex.CodingChallenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.hex.CodingChallenge.Enum.Speciality;
import com.hex.CodingChallenge.Exception.ResourceNotFoundException;
import com.hex.CodingChallenge.Model.Doctor;
import com.hex.CodingChallenge.Model.Patient;
import com.hex.CodingChallenge.Model.User;
import com.hex.CodingChallenge.Repository.DoctorRepository;
import com.hex.CodingChallenge.Repository.PatientRepository;
import com.hex.CodingChallenge.Service.PatientService;

@SpringBootTest
public class GetAllPatientsByDoctorIdTestMock {

	@InjectMocks
	private PatientService patientService;
	@Mock
	private PatientRepository patientRepository;
	@Mock
	private DoctorRepository doctorRepository;

	User user = new User();
	Patient patient = new Patient();
	Doctor doctor = new Doctor();

	@BeforeEach
	public void setup() {
		patient.setId(1);
		patient.setName("Raju");
		patient.setAge(44);

		user.setId(2);
		user.setUsername("raju@gmail.com");
		user.setPassword("raju@123");

		patient.setUser(user);

		doctor.setId(1);
		doctor.setName("Harri");
		doctor.setSpeciality(Speciality.ORTHO);

		user.setId(1);
		user.setUsername("harri@gmail.com");
		user.setPassword("harri@123");

		doctor.setUser(user);
	}

	@Test
	public void getByDoctorTest() {
		when(doctorRepository.getByUsername("harri@gmail.com")).thenReturn(Optional.of(doctor));
		
		List<Patient> list=List.of(patient);
		when(patientRepository.getByDoctor(doctor.getId())).thenReturn(list);
		
		assertEquals(list,patientService.getByDoctor("harri@gmail.com"));
		
		//Wrong input
		ResourceNotFoundException e=assertThrows(ResourceNotFoundException.class,()->{
			patientService.getByDoctor("hari@gmail.com");
		});
		
	}
}
