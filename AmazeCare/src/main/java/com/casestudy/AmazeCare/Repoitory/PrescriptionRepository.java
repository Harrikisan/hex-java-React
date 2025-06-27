package com.casestudy.AmazeCare.Repoitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.casestudy.AmazeCare.Model.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {
    
	@Query("select p from Prescription p where p.medicalRecord.id=?1")
	List<Prescription> getByMedicalRecordId(int medicalRecordId);
}
