package com.casestudy.AmazeCare.Repoitory;



import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.casestudy.AmazeCare.Enum.UserStatus;
import com.casestudy.AmazeCare.Model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer>{

	@Query("select d from Doctor d where d.user.username=?1")
	Optional<Doctor> getByUsername(String username);

	@Query("select d from Doctor d where d.name=?1 and d.userStatus=ACTIVE")
	Page<Doctor> getByName(String doctorName,Pageable pageable);

	@Query("select d from Doctor d where d.userStatus=ACTIVE")
	Page<Doctor> getAllActive(Pageable pageable);

	@Query("select d from Doctor d where d.id=?1 and d.userStatus=ACTIVE")
	Optional<Doctor> getByActiveId(int doctor_id);

	@Query("select d from Doctor d where d.specilization=?1 and d.userStatus=ACTIVE")
	List<Doctor> getBySpecilization(String speclization,Pageable pageable);

	@Query("select d from Doctor d where d.name=?1 and d.userStatus=ACTIVE")
	List<Doctor> getByNameSearch(String name);

	
	

}
