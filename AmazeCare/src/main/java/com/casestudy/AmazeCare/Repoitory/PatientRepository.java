package com.casestudy.AmazeCare.Repoitory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.casestudy.AmazeCare.Model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer>{

	@Query("select p from Patient p where p.user.username=?1 and p.userStatus=ACTIVE")
	Patient getbyUsername(String name);

	@Query("select p from Patient p where p.name=?1 and p.userStatus=ACTIVE")
	List<Patient> getbyName(String patientName,Pageable page);

	@Query("select p from Patient p where p.id=?1 and p.userStatus=ACTIVE")
	Optional<Patient> getPatientById(int patient_id);

	@Query("select p from Patient p where p.userStatus=ACTIVE")
	List<Patient> getAll(PageRequest page);
	

}
