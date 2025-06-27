package com.casestudy.AmazeCare.Service;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.casestudy.AmazeCare.Enum.Role;
import com.casestudy.AmazeCare.Exception.DoctorNotFoundException;
import com.casestudy.AmazeCare.Model.Doctor;
import com.casestudy.AmazeCare.Model.Nurse;
import com.casestudy.AmazeCare.Model.User;
import com.casestudy.AmazeCare.Repoitory.AdminRepository;
import com.casestudy.AmazeCare.Repoitory.DoctorRepository;
import com.casestudy.AmazeCare.Repoitory.NurseRepository;
import com.casestudy.AmazeCare.Repoitory.PatientRepository;
import com.casestudy.AmazeCare.Repoitory.UserRepository;
import com.casestudy.AmazeCare.Util.JwtUtil;

@Service
public class UserService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private DoctorRepository doctorRepository;
	private PatientRepository patientRepository;
	private NurseRepository nurseRepository;
	private AdminRepository adminRepository;

	@Autowired
	private JwtUtil jwtUtil;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
			DoctorRepository doctorRepository, PatientRepository patientRepository, NurseRepository nurseRepository,
			AdminRepository adminRepository) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.doctorRepository = doctorRepository;
		this.patientRepository = patientRepository;
		this.nurseRepository = nurseRepository;
		this.adminRepository = adminRepository;
	}



	public User addUser(User user) {
		//encrypt password
		String plaintext=user.getPassword();
		String ciphertext=passwordEncoder.encode(plaintext);
		
		//Set encrypted password
		user.setPassword(ciphertext);
		
		//Save to DB
		return userRepository.save(user);
	}



	public Object getToken(Principal principal) {
		
		try {
			String token =jwtUtil.createToken(principal.getName()); 
			return ResponseEntity.status(HttpStatus.OK).body(token);
			}
			catch(Exception e){
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
			}
	}


	public Object getUserInfo(String username) {
		User user=userRepository.getByUsername(username);
		switch(user.getRole()) {
		case Role.DOCTOR:
			return doctorRepository.getByUsername(username)
			.orElseThrow(()->new DoctorNotFoundException("Account not active"));
		case Role.NURSE:
			return nurseRepository.getByUsername(username);
		case Role.PATIENT:
			return patientRepository.getbyUsername(username);
		case Role.ADMIN:
			return adminRepository.getByUsername(username);
		}
		
		return null;
	}



	
	
	
	
}
