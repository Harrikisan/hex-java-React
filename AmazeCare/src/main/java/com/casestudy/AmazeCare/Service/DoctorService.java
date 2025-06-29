package com.casestudy.AmazeCare.Service;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.casestudy.AmazeCare.Dto.DoctorListingDto;
import com.casestudy.AmazeCare.Dto.DoctorSearchDto;
import com.casestudy.AmazeCare.Dto.TodaysAppointmentDto;
import com.casestudy.AmazeCare.Enum.AppointmentStatus;
import com.casestudy.AmazeCare.Enum.Day;
import com.casestudy.AmazeCare.Enum.Role;
import com.casestudy.AmazeCare.Enum.ScheduleStatus;
import com.casestudy.AmazeCare.Enum.Slot;
import com.casestudy.AmazeCare.Enum.UserStatus;
import com.casestudy.AmazeCare.Exception.DoctorNotFoundException;
import com.casestudy.AmazeCare.Model.Doctor;
import com.casestudy.AmazeCare.Model.Patient;
import com.casestudy.AmazeCare.Model.User;
import com.casestudy.AmazeCare.Repoitory.DoctorAppointmentRepository;
import com.casestudy.AmazeCare.Repoitory.DoctorRepository;
@Service
public class DoctorService {

	private DoctorRepository doctorRepository;
	private DoctorAppointmentRepository doctorAppointmentRepository;

	private DoctorScheduleService doctorScheduleService;

	public UserService userService;
	public DoctorListingDto doctorListingDto;
	public DoctorSearchDto doctorSearchDto;
	public TodaysAppointmentDto todaysAppointmentDto;

	public DoctorService(DoctorRepository doctorRepository, DoctorAppointmentRepository doctorAppointmentRepository,
			DoctorScheduleService doctorScheduleService, UserService userService, DoctorListingDto doctorListingDto,
			DoctorSearchDto doctorSearchDto, TodaysAppointmentDto todaysAppointmentDto) {
		super();
		this.doctorRepository = doctorRepository;
		this.doctorAppointmentRepository = doctorAppointmentRepository;
		this.doctorScheduleService = doctorScheduleService;
		this.userService = userService;
		this.doctorListingDto = doctorListingDto;
		this.doctorSearchDto = doctorSearchDto;
		this.todaysAppointmentDto = todaysAppointmentDto;
	}

	private Logger logger = LoggerFactory.getLogger(DoctorService.class);
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

		doctor = doctorRepository.save(doctor);

		for (Day day : Day.values()) {
			for (Slot slot : Slot.values()) {
				doctorScheduleService.addSchedule(doctor.getId(), day, slot, ScheduleStatus.NOTAVAILABLE);
			}
		}

		return doctor;
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

	public Page<Doctor> getByName(String name, int page, int size) {
	    Pageable pageable = PageRequest.of(page, size);
	    return doctorRepository.getByName(name, pageable);
	}


	public List<DoctorListingDto> getBySpecilization(String speclization, int page, int size) {
		List<Doctor> doctor;
		// Get Doctor details
		Pageable pageable = PageRequest.of(page, size);
		return doctorListingDto.convertToDto(doctorRepository.getBySpecilization(speclization, pageable));
	}

	public List<DoctorSearchDto> getByNameSearch(String name) {

		return doctorSearchDto.convertToDto(doctorRepository.getByNameSearch(name));
	}

	public Doctor updateInfo(String username, Doctor doctor) {
		// Fetch olddoctor
		Doctor oldDoctor = doctorRepository.getByUsername(username)
				.orElseThrow(() -> new DoctorNotFoundException("Wrong credentials"));
		// Attach to doctor
		doctor.setId(oldDoctor.getId());
		doctor.setUser(oldDoctor.getUser());
		if (doctor.getAddress() == null)
			doctor.setAddress(oldDoctor.getAddress());
		if (doctor.getEmail() == null)
			doctor.setEmail(oldDoctor.getEmail());
		if (doctor.getName() == null)
			doctor.setName(oldDoctor.getName());
		if (doctor.getSpecilization() == null)
			doctor.setSpecilization(oldDoctor.getSpecilization());
		if (doctor.getPhone() == null)
			doctor.setPhone(oldDoctor.getPhone());
		if (doctor.getQualification() == null)
			doctor.setQualification(oldDoctor.getQualification());
		if (doctor.getYearsOfExperience() == null)
			doctor.setYearsOfExperience(oldDoctor.getYearsOfExperience());
		// Add to db
		return doctorRepository.save(doctor);
	}

	public Doctor InactivateDoctor(int doctor_id) {
		Doctor doctor = doctorRepository.findById(doctor_id)
				.orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));
		doctor.setUserStatus(UserStatus.INACTIVE);
		return doctorRepository.save(doctor);

	}

	public Doctor EditStatus(int doctor_id, UserStatus status) {
		Doctor doctor = doctorRepository.findById(doctor_id)
				.orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));
		doctor.setUserStatus(status);
		return doctorRepository.save(doctor);
	}

	public List<Patient> getMypatients(String username) {
		Doctor doctor = doctorRepository.getByUsername(username)
				.orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));
		return doctorAppointmentRepository.getPatientByDoctorId(doctor.getId());
	}

	public List<TodaysAppointmentDto> getTodaysAppointment(String username, LocalDate date) {
		Doctor doctor = doctorRepository.getByUsername(username)
				.orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));
		return todaysAppointmentDto.convertToDto(
				doctorAppointmentRepository.getTodaysAppointment(doctor.getId(), date, AppointmentStatus.APPOROVED));
	}
	
	public Doctor getByUsername(String username) {
	    return doctorRepository.getByUsername(username)
	           .orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));
	}
	
	public Doctor uploadProfilePic(MultipartFile file, String username) throws IOException {
        Doctor doctor = doctorRepository.getByUsername(username)
        		.orElseThrow(()->new DoctorNotFoundException("Doctor not found"));
        if (doctor == null) {
            throw new RuntimeException("Doctor not found for username: " + username);
        }

        logger.info("Doctor found: " + doctor.getName());

        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1).toLowerCase();

        if (!(List.of("jpg", "jpeg", "png", "gif", "svg").contains(extension))) {
            logger.error("Extension not allowed: " + extension);
            throw new RuntimeException("File Extension '" + extension + "' not allowed. Allowed: jpg, jpeg, png, gif, svg");
        }

        long kbs = file.getSize() / 1024;
        if (kbs > 3000) {
            logger.error("File size too large: " + kbs + "KB");
            throw new RuntimeException("Image Oversized. Max allowed size is 3000 KB");
        }

        logger.info("Image size: " + kbs + "KB");

        String uploadFolder = "D:\\HEXAWARE-TRAINING\\JAVA-REACT\\React\\hms_ui\\public\\images";
        Files.createDirectories(Path.of(uploadFolder));

        Path filePath = Paths.get(uploadFolder, originalFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        doctor.setImageUrl(originalFileName);
        return doctorRepository.save(doctor);
    }


}
