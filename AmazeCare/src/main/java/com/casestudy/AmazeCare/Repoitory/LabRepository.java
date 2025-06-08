package com.casestudy.AmazeCare.Repoitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.casestudy.AmazeCare.Enum.LabStatus;
import com.casestudy.AmazeCare.Model.Lab;

public interface LabRepository extends JpaRepository<Lab, Integer>{

	@Query("select l from Lab l where l.labStatus=AVAILABLE")
	List<Lab> getAll();

	

	
}
