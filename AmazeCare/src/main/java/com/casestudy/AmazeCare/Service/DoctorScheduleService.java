package com.casestudy.AmazeCare.Service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.casestudy.AmazeCare.Enum.Slot;
import com.casestudy.AmazeCare.Exception.DoctorNotFoundException;
import com.casestudy.AmazeCare.Model.Doctor;
import com.casestudy.AmazeCare.Model.DoctorAppointment;
import com.casestudy.AmazeCare.Model.DoctorSchedule;
import com.casestudy.AmazeCare.Repoitory.DoctorAppointmentRepository;
import com.casestudy.AmazeCare.Repoitory.DoctorRepository;
import com.casestudy.AmazeCare.Repoitory.DoctorScheduleRepository;

@Service
public class DoctorScheduleService {

	private DoctorScheduleRepository doctorScheduleRepository;
	private DoctorRepository doctorRepository;
	private DoctorAppointmentRepository doctorAppointmentRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(DoctorAppointmentService.class);

	public DoctorScheduleService(DoctorScheduleRepository doctorScheduleRepository, DoctorRepository doctorRepository,
			DoctorAppointmentRepository doctorAppointmentRepository) {
		super();
		this.doctorScheduleRepository = doctorScheduleRepository;
		this.doctorRepository = doctorRepository;
		this.doctorAppointmentRepository = doctorAppointmentRepository;
	}

	public DoctorSchedule addSchedule(String username, DoctorSchedule doctorSchedule) {
		// fetch doctor
		Doctor doctor = doctorRepository.getByUsername(username)
				.orElseThrow(() -> new DoctorNotFoundException("Doctor not available or active"));
		// add to schedule
		doctorSchedule.setDoctor(doctor);
		// add to db
		return doctorScheduleRepository.save(doctorSchedule);
	}

	public List<DoctorSchedule> getByDoctor(int doctor_id) {
		return doctorScheduleRepository.getByDoctor(doctor_id);
	}

	public List<DoctorSchedule> getAvailableSoltsByDoctorId(int doctor_id) {
		// fetch slots which are booked less than two times in appointment table
		return doctorScheduleRepository.getAvailableSlots(doctor_id);
	}

	public List<Slot> getAvailableSlotsForDate(int doctor_id, LocalDate date) {
		return doctorScheduleRepository.getAvailableSlotsForDate(doctor_id,date);
	}

	public void deleteSchedule(int schedule_id) {
		doctorScheduleRepository.deleteSchedule(schedule_id);
	}

}
