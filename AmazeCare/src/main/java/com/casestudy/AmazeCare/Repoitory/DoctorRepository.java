package com.casestudy.AmazeCare.Repoitory;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.casestudy.AmazeCare.Model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer>{

	@Query("select d from Doctor d where d.user.username=?1")
	Optional<Doctor> getByUsername(String username);

	@Query("select d from Doctor d where d.name=?1")
	Doctor getByName(String doctorName);

}
