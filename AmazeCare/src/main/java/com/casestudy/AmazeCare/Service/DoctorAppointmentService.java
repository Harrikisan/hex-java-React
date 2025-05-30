package com.casestudy.AmazeCare.Service;

import org.springframework.stereotype.Service;

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
	
	
	
	
}
