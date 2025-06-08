package com.casestudy.AmazeCare.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casestudy.AmazeCare.Dto.DoctorAppointmentDto;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DoctorAppointmentService {

	private DoctorAppointmentRepository doctorAppointmentRepository;
	private PatientRepository patientRepository;
	private DoctorScheduleRepository doctorScheduleRepository;
	private DoctorRepository doctorRepository;
	
	@Autowired
	private DoctorAppointmentDto doctorAppointmentDto;
	
	private static final Logger logger = LoggerFactory.getLogger(DoctorAppointmentService.class);
	
	public DoctorAppointmentService(DoctorAppointmentRepository doctorAppointmentRepository,
			PatientRepository patientRepository, DoctorScheduleRepository doctorScheduleRepository,
			DoctorRepository doctorRepository) {
		super();
		this.doctorAppointmentRepository = doctorAppointmentRepository;
		this.patientRepository = patientRepository;
		this.doctorScheduleRepository = doctorScheduleRepository;
		this.doctorRepository = doctorRepository;
	}


	public DoctorAppointment addDoctorAppointment(int patient_id, int doctor_id, int schedule_id,
			DoctorAppointment doctorAppointment) {
		//fetch patient
		Patient patient=patientRepository.findById(patient_id).
				orElseThrow(()->new PatientNotFountException("Patient ID not found"));
		//fetch doctor
		Doctor doctor=doctorRepository.findById(doctor_id).
				orElseThrow(()->new DoctorNotFoundException("Doctor ID not found"));
		
		//fetch schedule
		DoctorSchedule doctorSchedule=doctorScheduleRepository.findById(schedule_id)
				.orElseThrow(()->new DoctorScheduleNotFoundException("Schedule ID not found"));
		//add to doctorAppointment
		doctorAppointment.setPatient(patient);
		doctorAppointment.setDoctor(doctor);
		doctorAppointment.setDoctorSchedule(doctorSchedule);
		//add to db
		return doctorAppointmentRepository.save(doctorAppointment);
	}
	
	public List<LocalDate> getAvailableDatesForDoctor(int doctor_id){
		List<LocalDate> dates=new ArrayList<>();
		for(int i=0;i<21;i++) {
			LocalDate date=LocalDate.now().plusDays(i);
			if(doctorAppointmentRepository.findByDoctorIdAndDate(doctor_id,date).size()<8) dates.add(date);
		}
		logger.info("Available dates for next 7 days: {}",dates);
		return dates;
	}


	public List<DoctorAppointmentDto> getByDoctor(String username) {
		Doctor doctor=doctorRepository.getByUsername(username)
				.orElseThrow(()->new PatientNotFountException("Patient ID not found"));
		return doctorAppointmentDto.convertToDto(doctorAppointmentRepository.getByDoctorId(doctor.getId()));
	}


	public List<DoctorAppointmentDto> getAppointmentByPatient(String username) {
		Patient patient=patientRepository.getbyUsername(username);
		return doctorAppointmentDto.convertToDto(doctorAppointmentRepository.getByPatientId(patient.getId()));
	}


	
	
	
}
