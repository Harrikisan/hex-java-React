package com.casestudy.AmazeCare.Repoitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.casestudy.AmazeCare.Model.Lab;

public interface LabRepository extends JpaRepository<Lab, Integer>{

	
}
