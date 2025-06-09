package com.hex.CodingChallenge.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hex.CodingChallenge.Exception.ResourceNotFoundException;
import com.hex.CodingChallenge.Model.Doctor;
import com.hex.CodingChallenge.Model.Patient;
import com.hex.CodingChallenge.Model.User;
import com.hex.CodingChallenge.Repository.DoctorRepository;
import com.hex.CodingChallenge.Repository.PatientRepository;

@Service
public class PatientService {

	private PatientRepository patientRepository;
	private DoctorRepository doctorRepository;

	@Autowired
	private UserService userService;
	
	


	public PatientService(PatientRepository patientRepository, DoctorRepository doctorRepository,
			UserService userService) {
		super();
		this.patientRepository = patientRepository;
		this.doctorRepository = doctorRepository;
		this.userService = userService;
	}


	public Patient add(Patient patient) {
		User user=patient.getUser();
		user.setRole("PATIENT");
		userService.addUser(user);
		patient.setUser(user);
		return patientRepository.save(patient);
	}


	public List<Patient> getByDoctor(String username) {
		Doctor doctor=doctorRepository.getByUsername(username)
				.orElseThrow(()->new ResourceNotFoundException("Doctor not found"));
		
		return patientRepository.getByDoctor(doctor.getId());
	}
}
