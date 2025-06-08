package com.casestudy.AmazeCare.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.casestudy.AmazeCare.Dto.DoctorListingDto;
import com.casestudy.AmazeCare.Dto.DoctorSearchDto;
import com.casestudy.AmazeCare.Enum.Role;
import com.casestudy.AmazeCare.Enum.UserStatus;
import com.casestudy.AmazeCare.Exception.DoctorNotFoundException;
import com.casestudy.AmazeCare.Model.Doctor;
import com.casestudy.AmazeCare.Model.User;
import com.casestudy.AmazeCare.Repoitory.DoctorRepository;

@Service
public class DoctorService {

	private DoctorRepository doctorRepository;
	public UserService userService;

	@Autowired
	public DoctorListingDto doctorListingDto;
	@Autowired
	public DoctorSearchDto doctorSearchDto;

	public DoctorService(DoctorRepository doctorRepository, UserService userService) {
		super();
		this.doctorRepository = doctorRepository;
		this.userService = userService;
	}

	public Doctor addDoctor(Doctor doctor) {
		// fetch user
		User user = doctor.getUser();

		// add userRole

		user.setRole(Role.DOCTOR);

		// add user to db
		user = userService.addUser(user);

		// set user to doctor
		doctor.setUser(user);
		doctor.setUserStatus(UserStatus.ACTIVE);

		return doctorRepository.save(doctor);
	}

	public Doctor getById(int doctor_id) {
		return doctorRepository.getByActiveId(doctor_id)
				.orElseThrow(() -> new DoctorNotFoundException("Doctor not active"));
	}

	public List<DoctorListingDto> getAllActive(int page, int size) {

		// Get Doctor details
		Pageable pageable = PageRequest.of(page, size);

		return doctorListingDto.convertToDto(doctorRepository.getAllActive(pageable).getContent());
	}

	public List<DoctorListingDto> getByName(String name, int page, int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		return doctorListingDto.convertToDto(doctorRepository.getByName(name, pageable));
	}

	public List<DoctorListingDto> getBySpecilization(String speclization, int page, int size) {
		List<Doctor> doctor;
		// Get Doctor details
		Pageable pageable = PageRequest.of(page, size);
		return doctorListingDto.convertToDto(doctorRepository.getBySpecilization(speclization,pageable));
	}

	public List<DoctorSearchDto> getByNameSearch(String name) {
		
		return doctorSearchDto.convertToDto(doctorRepository.getByNameSearch(name));
	}

	public Doctor updateInfo(String username, Doctor doctor) {
		//Fetch olddoctor
		Doctor oldDoctor=doctorRepository.getByUsername(username)
				.orElseThrow(()->new DoctorNotFoundException("Wrong credentials"));
		//Attach to doctor
		doctor.setId(oldDoctor.getId());
		doctor.setUser(oldDoctor.getUser());
		if(doctor.getAddress()==null) doctor.setAddress(oldDoctor.getAddress());
		if(doctor.getEmail()==null) doctor.setEmail(oldDoctor.getEmail());
		if(doctor.getName()==null) doctor.setName(oldDoctor.getName());
		if(doctor.getSpecilization()==null) doctor.setSpecilization(oldDoctor.getSpecilization());
		if(doctor.getPhone()==null) doctor.setPhone(oldDoctor.getPhone());
		if(doctor.getQualification()==null) doctor.setQualification(oldDoctor.getQualification());
		if(doctor.getYearsOfExperience()==null) doctor.setYearsOfExperience(oldDoctor.getYearsOfExperience());
		//Add to db
		return doctorRepository.save(doctor);
	}

	public Doctor InactivateDoctor(int doctor_id) {
		Doctor doctor=doctorRepository.findById(doctor_id)
				.orElseThrow(()->new DoctorNotFoundException("Doctor not found"));
		doctor.setUserStatus(UserStatus.INACTIVE);
		return doctorRepository.save(doctor);
		
	}

	public Doctor EditStatus(int doctor_id,UserStatus status) {
		Doctor doctor=doctorRepository.findById(doctor_id)
				.orElseThrow(()->new DoctorNotFoundException("Doctor not found"));
		doctor.setUserStatus(status);
		return doctorRepository.save(doctor);
	}

}
