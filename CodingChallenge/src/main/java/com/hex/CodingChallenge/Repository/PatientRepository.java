package com.hex.CodingChallenge.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hex.CodingChallenge.Model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer>{

	@Query("select p from Patient p join Appointment a on p.id = a.patient.id where a.doctor.id = ?1")
	List<Patient> getByDoctor(int doctor_id);

}
