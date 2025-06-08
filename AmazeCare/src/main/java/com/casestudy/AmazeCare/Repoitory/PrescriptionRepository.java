package com.casestudy.AmazeCare.Repoitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.casestudy.AmazeCare.Model.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {
    List<Prescription> findByMedicalRecordId(int medicalRecordId);
}
