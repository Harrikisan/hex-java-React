package com.casestudy.AmazeCare.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.casestudy.AmazeCare.Enum.Day;
import com.casestudy.AmazeCare.Enum.ScheduleStatus;
import com.casestudy.AmazeCare.Enum.Slot;
import com.casestudy.AmazeCare.Exception.DoctorNotFoundException;
import com.casestudy.AmazeCare.Exception.DoctorScheduleNotFoundException;
import com.casestudy.AmazeCare.Model.Doctor;
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
		logger.info("schedule",doctorSchedule);
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

	public List<DoctorSchedule> getAvailableSlotsForDate(int doctor_id, LocalDate date) {
		return doctorScheduleRepository.getAvailableSlotsForDate(doctor_id,date,Day.valueOf(date.getDayOfWeek().toString()));
	}

	public void deleteSchedule(int schedule_id) {
		doctorScheduleRepository.deleteSchedule(schedule_id);
	}

	public DoctorSchedule addSchedule(int doctorId, DoctorSchedule schedule) {
		Doctor doctor=doctorRepository.findById(doctorId)
				.orElseThrow(()->new DoctorNotFoundException("Doctor not found"));
		schedule.setDoctor(doctor);
		return doctorScheduleRepository.save(schedule);
	}

	public void addSchedule(int id, Day day, Slot slot, ScheduleStatus status) {
		DoctorSchedule schedule=new DoctorSchedule();
		Doctor doctor=doctorRepository.findById(id)
				.orElseThrow(()->new DoctorNotFoundException("Doctor not found"));
		schedule.setDoctor(doctor);
		schedule.setDay(day);
		schedule.setSlot(slot);
		schedule.setStatus(status);
		doctorScheduleRepository.save(schedule);
	}

	public List<DoctorSchedule> getByDoctor(String username) {
		Doctor doctor=doctorRepository.getByUsername(username)
				.orElseThrow(()->new DoctorNotFoundException("Doctor not found"));
		return doctorScheduleRepository.getByDoctor(doctor.getId());
	}

	public DoctorSchedule editStatus(int recordId, String status) {
		DoctorSchedule doctorSchedule=doctorScheduleRepository.findById(recordId)
				.orElseThrow(()->new DoctorScheduleNotFoundException("record not found"));
		doctorSchedule.setStatus(ScheduleStatus.valueOf(status));
		return doctorScheduleRepository.save(doctorSchedule);
	}

}
