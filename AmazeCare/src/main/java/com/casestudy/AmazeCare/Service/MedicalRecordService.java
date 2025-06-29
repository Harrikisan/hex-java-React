package com.casestudy.AmazeCare.Service;

import java.time.LocalDate;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.casestudy.AmazeCare.Exception.MedicalRecordNotFoundException;
import com.casestudy.AmazeCare.Exception.PatientNotFountException;
import com.casestudy.AmazeCare.Model.Doctor;
import com.casestudy.AmazeCare.Model.MedicalRecord;
import com.casestudy.AmazeCare.Model.Patient;
import com.casestudy.AmazeCare.Repoitory.DoctorAppointmentRepository;
import com.casestudy.AmazeCare.Repoitory.DoctorRepository;
import com.casestudy.AmazeCare.Repoitory.MedicalRecordRepository;
import com.casestudy.AmazeCare.Repoitory.PatientRepository;

@Service
public class MedicalRecordService {

    private  MedicalRecordRepository medicalRecordRepository;
    private  DoctorRepository doctorRepository;
    private  DoctorAppointmentRepository appointmentRepository;
    private PatientRepository patientRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(DoctorAppointmentService.class);

    

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository, DoctorRepository doctorRepository,
			DoctorAppointmentRepository appointmentRepository, PatientRepository patientRepository) {
		super();
		this.medicalRecordRepository = medicalRecordRepository;
		this.doctorRepository = doctorRepository;
		this.appointmentRepository = appointmentRepository;
		this.patientRepository = patientRepository;
	}

	// Add medical record with doctor and appointment associations
    public MedicalRecord addMedicalRecord(MedicalRecord record, int patientId, String username) {
        Doctor doctor = doctorRepository.getByUsername(username)
                .orElseThrow(() -> new MedicalRecordNotFoundException("Doctor not found"));
        Patient patient=patientRepository.findById(patientId)
        		.orElseThrow(()->new PatientNotFountException("Patient not found"));
        record.setPatient(patient);
        record.setDoctor(doctor);
        record.setRecordDate(LocalDate.now());
        
        logger.info("record added: "+record);
        return medicalRecordRepository.save(record);
    }

    // Get all records
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordRepository.findAll();
    }

    // Get by id
    public MedicalRecord getMedicalRecordById(int id) {
        return medicalRecordRepository.findById(id)
                .orElseThrow(() -> new MedicalRecordNotFoundException("Medical record not found with id: " + id));
    }

    // Get medical records by patient's username
    public List<MedicalRecord> getMedicalRecordsByPatientUsername(String username) {
        Patient patient = patientRepository.getbyUsername(username);
        return medicalRecordRepository.findByPatientUsername(patient.getId());
    }
  
    public List<MedicalRecord> getMedicalRecordsByPatientId(int patientId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MedicalRecord> recordsPage = medicalRecordRepository.findByPatientId(patientId, pageable);
        return recordsPage.getContent();
    }

    // Get medical records by doctor's username
    public List<MedicalRecord> getMedicalRecordsByDoctorUsername(String username) {
        Doctor doctor = doctorRepository.getByUsername(username)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        return medicalRecordRepository.findByDoctorId(doctor.getId());
    }

    // Get records by record date
    public List<MedicalRecord> getMedicalRecordsByRecordDate(LocalDate recordDate) {
        return medicalRecordRepository.findByRecordDate(recordDate);
    }

	
}
