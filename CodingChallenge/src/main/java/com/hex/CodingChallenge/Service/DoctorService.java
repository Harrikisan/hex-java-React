package com.hex.CodingChallenge.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hex.CodingChallenge.Model.Doctor;
import com.hex.CodingChallenge.Model.User;
import com.hex.CodingChallenge.Repository.DoctorRepository;

@Service
public class DoctorService {

	private DoctorRepository doctorRepository;

	@Autowired
	private UserService userService;
	
	public DoctorService(DoctorRepository doctorRepository) {
		super();
		this.doctorRepository = doctorRepository;
	}

	public Doctor add(Doctor doctor) {
		User user=doctor.getUser();
		user.setRole("DOCTOR");
		user=userService.addUser(user);
		doctor.setUser(user);
		return doctorRepository.save(doctor);
	}
	
	
}
