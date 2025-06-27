package com.casestudy.AmazeCare.Repoitory;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.casestudy.AmazeCare.Model.MedicalRecord;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Integer> {

    @Query("select mr from MedicalRecord mr where mr.patient.id = ?1")
    List<MedicalRecord> findByPatientUsername(int patientId);

    @Query("select mr from MedicalRecord mr where mr.patient.id = ?1")
    Page<MedicalRecord> findByPatientId(int patientId, Pageable pageable);
    
    @Query("select mr from MedicalRecord mr where mr.doctor.id = ?1")
    List<MedicalRecord> findByDoctorId(int doctorId);

    List<MedicalRecord> findByRecordDate(LocalDate recordDate);
    
    
}
