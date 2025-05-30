package com.casestudy.AmazeCare.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.casestudy.AmazeCare.Enum.Role;
import com.casestudy.AmazeCare.Model.Patient;
import com.casestudy.AmazeCare.Model.User;
import com.casestudy.AmazeCare.Repoitory.PatientRepository;

@Service
public class PatientService {

	public PatientRepository patientRepository;
	public UserService userService;

	public PatientService(PatientRepository patientRepository, UserService userService) {
		super();
		this.patientRepository = patientRepository;
		this.userService = userService;
	}

	public Patient insertPatient(Patient patient) {
		// get User
		User user = patient.getUser();

		// set User role
		user.setRole(Role.PATIENT);

		// Signup user
		user = userService.addUser(user);

		// set patient user
		patient.setUser(user);

		return patientRepository.save(patient);
	}

	public List<Patient> getAll() {
		return patientRepository.findAll();
	}

	public Patient getName(String name) {
		return patientRepository.getbyName(name);
	}

	public Patient editPatientInfo(String username, Patient patient) {
		// Check whether patient id already exists , or throw exception. Patient
		Patient oldPatient = patientRepository.getbyName(username);
		// Set values for old values
		if (patient.getName() != null)
			oldPatient.setName(patient.getName());
		if (patient.getEmail() != null)
			oldPatient.setEmail(patient.getEmail());
		if (patient.getAddress() != null)
			oldPatient.setAddress(patient.getAddress());
		if (patient.getDob() != null)
			oldPatient.setDob(patient.getDob());
		if (patient.getEmergencyContact() != null)
			oldPatient.setEmergencyContact(patient.getEmergencyContact());
		if (patient.getGender() != null)
			oldPatient.setGender(patient.getGender());
		// save to db
		return patientRepository.save(oldPatient);
	}

}
