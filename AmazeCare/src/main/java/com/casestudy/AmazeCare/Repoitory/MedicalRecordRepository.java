package com.casestudy.AmazeCare.Repoitory;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.casestudy.AmazeCare.Model.MedicalRecord;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Integer> {

    @Query("select mr from MedicalRecord mr where mr.appointment.patient.id = ?1")
    List<MedicalRecord> findByPatientId(int patientId);

    @Query("select mr from MedicalRecord mr where mr.appointment.doctor.id = ?1")
    List<MedicalRecord> findByDoctorId(int doctorId);

    List<MedicalRecord> findByRecordDate(LocalDate recordDate);
}
