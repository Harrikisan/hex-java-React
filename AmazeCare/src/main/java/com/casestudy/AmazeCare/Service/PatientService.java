package com.casestudy.AmazeCare.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.casestudy.AmazeCare.Dto.PatientListingDto;
import com.casestudy.AmazeCare.Enum.Role;
import com.casestudy.AmazeCare.Enum.UserStatus;
import com.casestudy.AmazeCare.Exception.PatientNotFountException;
import com.casestudy.AmazeCare.Model.Patient;
import com.casestudy.AmazeCare.Model.User;
import com.casestudy.AmazeCare.Repoitory.PatientRepository;

@Service
public class PatientService {

	public PatientRepository patientRepository;
	public UserService userService;

	@Autowired
	public PatientListingDto patientListingDto;
	
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
		return patientRepository.getbyUsername(name);
	}

	public Patient editPatientInfo(String username, Patient patient) {
		// Check whether patient id already exists , or throw exception. Patient
		Patient oldPatient = patientRepository.getbyUsername(username);
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

	public Patient getById(int patient_id) {
		return patientRepository.getPatientById(patient_id)
				.orElseThrow(()->new PatientNotFountException("Patient not Available or Active"));
	}

	public List<PatientListingDto> getByName(String name,int page,int size) {
		return patientListingDto.convertToDto(patientRepository.getbyName(name,PageRequest.of(page,size))) ;
	}

	public List<PatientListingDto> getAll(int page, int size) {
		return patientListingDto.convertToDto(patientRepository.getAll(PageRequest.of(page, size)));
	}

	public Patient editStatus(UserStatus status,int patient_id) {
		Patient patient=patientRepository.findById(patient_id)
				.orElseThrow(()->new PatientNotFountException("Patient not Available"));
		patient.setUserStatus(status);
		return patientRepository.save(patient);
	}

}
