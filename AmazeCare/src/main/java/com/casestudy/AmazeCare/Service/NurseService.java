package com.casestudy.AmazeCare.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casestudy.AmazeCare.Enum.Role;
import com.casestudy.AmazeCare.Enum.UserStatus;
import com.casestudy.AmazeCare.Exception.NurseNotFoundException;
import com.casestudy.AmazeCare.Model.Nurse;
import com.casestudy.AmazeCare.Model.User;
import com.casestudy.AmazeCare.Repoitory.NurseRepository;

@Service
public class NurseService {

	private final NurseRepository nurseRepository;
    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(DoctorAppointmentService.class);
    
    public NurseService(NurseRepository nurseRepository, UserService userService) {
        this.nurseRepository = nurseRepository;
        this.userService = userService;
    }

	public Nurse AddNurse(Nurse nurse) {
		User user=nurse.getUser();
		user.setRole(Role.NURSE);
		user=userService.addUser(user);
		nurse.setUser(user);
		logger.info("edited data before save",nurse);
		return nurseRepository.save(nurse);
	}

	

	public Nurse editStatus(int nurse_id, UserStatus status) {
		Nurse nurse=nurseRepository.findById(nurse_id).orElseThrow(()->new NurseNotFoundException("Nurse not available"));
		nurse.setUserStatus(status);
		return nurseRepository.save(nurse);
	}

	public Nurse UpdateNurse(String username, Nurse nurse) {
		Nurse oldNurse=nurseRepository.getByUsername(username);
		
		nurse.setId(oldNurse.getId());
		nurse.setUser(oldNurse.getUser());
		nurse.setUserStatus(oldNurse.getUserStatus());
		if(nurse.getName()==null) nurse.setName(oldNurse.getName());
		if(nurse.getEmail()==null) nurse.setEmail(oldNurse.getEmail());
		if(nurse.getPhone()==null) nurse.setPhone(oldNurse.getPhone());
		if(nurse.getAddress()==null) nurse.setAddress(oldNurse.getAddress());
		
		return nurseRepository.save(nurse);
	}
	
	public Nurse getNurseByUsername(String username) {
	    return nurseRepository.getByUsername(username);
	}

	
}
