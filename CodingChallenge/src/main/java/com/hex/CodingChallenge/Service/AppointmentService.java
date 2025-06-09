package com.hex.CodingChallenge.Service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.hex.CodingChallenge.Exception.ResourceNotFoundException;
import com.hex.CodingChallenge.Model.Appointment;
import com.hex.CodingChallenge.Model.Doctor;
import com.hex.CodingChallenge.Model.Patient;
import com.hex.CodingChallenge.Repository.AppointmentRepository;
import com.hex.CodingChallenge.Repository.DoctorRepository;
import com.hex.CodingChallenge.Repository.PatientRepository;

@Service
public class AppointmentService {

	private AppointmentRepository appointmentRepository;
	private PatientRepository patientRepository;
	private DoctorRepository doctorRepository;
	
	
	public AppointmentService(AppointmentRepository appointmentRepository, PatientRepository patientRepository,
			DoctorRepository doctorRepository) {
		super();
		this.appointmentRepository = appointmentRepository;
		this.patientRepository = patientRepository;
		this.doctorRepository = doctorRepository;
	}


	public Appointment add(int patient_id, int doctor_id) {
		//Fetch patient
		Patient patient=patientRepository.findById(patient_id)
				.orElseThrow(()->new ResourceNotFoundException("Patient id not found"));
		//Fetch doctor
		Doctor doctor=doctorRepository.findById(doctor_id)
				.orElseThrow(()->new ResourceNotFoundException("Doctor id not found"));
		Appointment appointment=new Appointment();
		appointment.setDate(LocalDate.now());
		appointment.setDoctor(doctor);
		appointment.setPatient(patient);
		return appointmentRepository.save(appointment);
	}

}
