package com.casestudy.AmazeCare.Repoitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.casestudy.AmazeCare.Enum.LabStatus;
import com.casestudy.AmazeCare.Model.Ward;

public interface WardRepository extends JpaRepository<Ward, Integer>{

	@Query("select w from Ward w where w.status=AVAILABLE")
	List<Ward> getAll();

	

}
