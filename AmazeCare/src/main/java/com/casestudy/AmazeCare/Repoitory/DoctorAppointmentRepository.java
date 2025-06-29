package com.casestudy.AmazeCare.Repoitory;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.casestudy.AmazeCare.Enum.AppointmentStatus;
import com.casestudy.AmazeCare.Model.Doctor;
import com.casestudy.AmazeCare.Model.DoctorAppointment;
import com.casestudy.AmazeCare.Model.Patient;

public interface DoctorAppointmentRepository extends JpaRepository<DoctorAppointment, Integer>{

	List<DoctorAppointment> findByDoctorIdAndDate(int doctorId, LocalDate date);

	@Query("select da from DoctorAppointment da where da.doctor.id=?1")
	List<DoctorAppointment> getByDoctorId(int id);

	@Query("select da from DoctorAppointment da where da.patient.id=?1")
	List<DoctorAppointment> getByPatientId(int id, Pageable pageable);
	
	@Query("select da.patient from DoctorAppointment da where da.doctor.id=?1")
	List<Patient> getPatientByDoctorId(int doctorid);

	@Query("select da from DoctorAppointment da where da.doctor.id = ?1 and da.date = ?2 and da.status = ?3")
	List<DoctorAppointment> getTodaysAppointment(int id, LocalDate date, AppointmentStatus status);

}
