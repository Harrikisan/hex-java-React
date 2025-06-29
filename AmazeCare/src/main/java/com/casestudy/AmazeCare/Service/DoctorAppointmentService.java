package com.casestudy.AmazeCare.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.casestudy.AmazeCare.Dto.AppointmentDateCountDto;
import com.casestudy.AmazeCare.Dto.DoctorAppointmentDto;
import com.casestudy.AmazeCare.Dto.PatientAppointmentCountDto;
import com.casestudy.AmazeCare.Enum.AppointmentStatus;
import com.casestudy.AmazeCare.Exception.AppointmentNotFoundException;
import com.casestudy.AmazeCare.Exception.DoctorNotFoundException;
import com.casestudy.AmazeCare.Exception.DoctorScheduleNotFoundException;
import com.casestudy.AmazeCare.Exception.PatientNotFountException;
import com.casestudy.AmazeCare.Model.Doctor;
import com.casestudy.AmazeCare.Model.DoctorAppointment;
import com.casestudy.AmazeCare.Model.DoctorSchedule;
import com.casestudy.AmazeCare.Model.Patient;
import com.casestudy.AmazeCare.Repoitory.DoctorAppointmentRepository;
import com.casestudy.AmazeCare.Repoitory.DoctorRepository;
import com.casestudy.AmazeCare.Repoitory.DoctorScheduleRepository;
import com.casestudy.AmazeCare.Repoitory.PatientRepository;

@Service
public class DoctorAppointmentService {

	private DoctorAppointmentRepository doctorAppointmentRepository;
	private PatientRepository patientRepository;
	private DoctorScheduleRepository doctorScheduleRepository;
	private DoctorRepository doctorRepository;

	private PatientAppointmentCountDto patientAppointmentCountDto;
	@Autowired
	private DoctorAppointmentDto doctorAppointmentDto;

	private static final Logger logger = LoggerFactory.getLogger(DoctorAppointmentService.class);

	public DoctorAppointmentService(DoctorAppointmentRepository doctorAppointmentRepository,
			PatientRepository patientRepository, DoctorScheduleRepository doctorScheduleRepository,
			DoctorRepository doctorRepository

	) {
		this.doctorAppointmentRepository = doctorAppointmentRepository;
		this.patientRepository = patientRepository;
		this.doctorScheduleRepository = doctorScheduleRepository;
		this.doctorRepository = doctorRepository;
	}

	public DoctorAppointment addDoctorAppointment(int patient_id, int doctor_id, int schedule_id,
			DoctorAppointment doctorAppointment) {
		Patient patient = patientRepository.findById(patient_id)
				.orElseThrow(() -> new PatientNotFountException("Patient ID not found"));

		Doctor doctor = doctorRepository.findById(doctor_id)
				.orElseThrow(() -> new DoctorNotFoundException("Doctor ID not found"));

		DoctorSchedule doctorSchedule = doctorScheduleRepository.findById(schedule_id)
				.orElseThrow(() -> new DoctorScheduleNotFoundException("Schedule ID not found"));

		doctorAppointment.setPatient(patient);
		doctorAppointment.setDoctor(doctor);
		doctorAppointment.setDoctorSchedule(doctorSchedule);
		doctorAppointment.setStatus(AppointmentStatus.APPOROVED); // Consider correcting typo if needed

		return doctorAppointmentRepository.save(doctorAppointment);
	}

	public List<LocalDate> getAvailableDatesForDoctor(int doctor_id) {
		List<LocalDate> dates = new ArrayList<>();
		for (int i = 0; i < 21; i++) {
			LocalDate date = LocalDate.now().plusDays(i);
			if (doctorAppointmentRepository.findByDoctorIdAndDate(doctor_id, date).size() < 8) {
				dates.add(date);
			}
		}
		logger.info("Available dates for next 7 days: {}", dates);
		return dates;
	}

	public List<DoctorAppointmentDto> getByDoctor(String username) {
		Doctor doctor = doctorRepository.getByUsername(username)
				.orElseThrow(() -> new PatientNotFountException("Patient ID not found"));
		return doctorAppointmentDto.convertToDto(doctorAppointmentRepository.getByDoctorId(doctor.getId()));
	}

	public List<DoctorAppointmentDto> getAppointmentByPatient(String username, int page, int size) {
		Patient patient = patientRepository.getbyUsername(username);

		Pageable pageable = PageRequest.of(page, size);
		List<DoctorAppointment> appointments = doctorAppointmentRepository.getByPatientId(patient.getId(), pageable);

		return doctorAppointmentDto.convertToDto(appointments);
	}

	public DoctorAppointment editAppointmentStatus(String status, int id) {
		DoctorAppointment appointment = doctorAppointmentRepository.findById(id)
				.orElseThrow(() -> new AppointmentNotFoundException("Appointment not found"));
		
			appointment.setStatus(AppointmentStatus.valueOf(status));
		return doctorAppointmentRepository.save(appointment);
	}

	public DoctorAppointment getAppointmentById(int id) {
		return doctorAppointmentRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
	}

	public PatientAppointmentCountDto getAppointmentCountGroupedByPatient(String username) {
	    // Step 1: Fetch doctor by username
	    Doctor doctor = doctorRepository.getByUsername(username)
	            .orElseThrow(() -> new RuntimeException("Doctor not found"));

	    // Step 2: Fetch all appointments for this doctor
	    List<DoctorAppointment> appointments = doctorAppointmentRepository.getByDoctorId(doctor.getId());

	    // Step 3: Initialize result arrays
	    List<Patient> patients = new ArrayList<>();
	    List<Integer> count = new ArrayList<>();

	    // Step 4: Group appointments by patient using just lists
	    for (DoctorAppointment appointment : appointments) {
	        Patient patient = appointment.getPatient();
	        int index = -1;

	        // Check if patient already in list
	        for (int i = 0; i < patients.size(); i++) {
	            if (patients.get(i).getId() == patient.getId()) {
	                index = i;
	                break;
	            }
	        }

	        if (index == -1) {
	            // New patient
	            patients.add(patient);
	            count.add(1);
	        } else {
	            // Existing patient - increment count
	            count.set(index, count.get(index) + 1);
	        }
	    }

	    // Step 5: Return as DTO
	    return new PatientAppointmentCountDto(patients, count);
	}
	
	public AppointmentDateCountDto getAppointmentCountByDate(String doctorUsername) {
	    // Step 1: Get doctor by username
	    Doctor doctor = doctorRepository.getByUsername(doctorUsername)
	            .orElseThrow(() -> new RuntimeException("Doctor not found"));

	    // Step 2: Get all appointments for this doctor
	    List<DoctorAppointment> appointments = doctorAppointmentRepository.getByDoctorId(doctor.getId());

	    // Step 3: Prepare lists
	    List<LocalDate> dates = new ArrayList<>();
	    List<Long> counts = new ArrayList<>();

	    for (DoctorAppointment appointment : appointments) {
	        LocalDate date = appointment.getDate();
	        int index = dates.indexOf(date);

	        if (index == -1) {
	            dates.add(date);
	            counts.add(1L);
	        } else {
	            counts.set(index, counts.get(index) + 1);
	        }
	    }

	    return new AppointmentDateCountDto(dates, counts);
	}



}
