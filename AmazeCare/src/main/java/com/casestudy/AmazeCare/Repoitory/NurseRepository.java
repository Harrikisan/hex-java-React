package com.casestudy.AmazeCare.Repoitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.casestudy.AmazeCare.Model.Nurse;

public interface NurseRepository extends JpaRepository<Nurse, Integer>{

	@Query("select n from Nurse n where n.user.username=?1")
	Nurse getByUsername(String username);
}
