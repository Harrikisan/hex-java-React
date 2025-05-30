package com.casestudy.AmazeCare.Service;

import org.springframework.stereotype.Service;

import com.casestudy.AmazeCare.Enum.Role;
import com.casestudy.AmazeCare.Enum.UserStatus;
import com.casestudy.AmazeCare.Model.Doctor;
import com.casestudy.AmazeCare.Model.User;
import com.casestudy.AmazeCare.Repoitory.DoctorRepository;

@Service
public class DoctorService {

	private DoctorRepository doctorRepository;
	public UserService userService;

	

	public DoctorService(DoctorRepository doctorRepository, UserService userService) {
		super();
		this.doctorRepository = doctorRepository;
		this.userService = userService;
	}



	public Doctor addDoctor(Doctor doctor) {
		//fetch user
		User user=doctor.getUser();
		
		//add userRole
		
		user.setRole(Role.DOCTOR);
		
		//add user to db
		user=userService.addUser(user);
		
		//set user to doctor
		doctor.setUser(user);
		doctor.setUserStatus(UserStatus.UNDERREVIEW);
		
		
		return doctorRepository.save(doctor);
	}

	
	
	
}
