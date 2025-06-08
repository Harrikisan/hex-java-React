package com.casestudy.AmazeCare.Repoitory;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.casestudy.AmazeCare.Model.DoctorAppointment;

public interface DoctorAppointmentRepository extends JpaRepository<DoctorAppointment, Integer>{

	List<DoctorAppointment> findByDoctorIdAndDate(int doctorId, LocalDate date);

	@Query("select da from DoctorAppointment da where da.doctor.id=?1")
	List<DoctorAppointment> getByDoctorId(int id);

	@Query("select da from DoctorAppointment da where da.patient.id=?1")
	List<DoctorAppointment> getByPatientId(int id);


	
	

}
