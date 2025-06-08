package com.casestudy.AmazeCare.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.casestudy.AmazeCare.Exception.MedicalRecordNotFoundException;
import com.casestudy.AmazeCare.Model.Doctor;
import com.casestudy.AmazeCare.Model.DoctorAppointment;
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

    

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository, DoctorRepository doctorRepository,
			DoctorAppointmentRepository appointmentRepository, PatientRepository patientRepository) {
		super();
		this.medicalRecordRepository = medicalRecordRepository;
		this.doctorRepository = doctorRepository;
		this.appointmentRepository = appointmentRepository;
		this.patientRepository = patientRepository;
	}

	// Add medical record with doctor and appointment associations
    public MedicalRecord addMedicalRecord(MedicalRecord record, int appointmentId, int doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new MedicalRecordNotFoundException("Doctor not found with id: " + doctorId));
        DoctorAppointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new MedicalRecordNotFoundException("Appointment not found with id: " + appointmentId));

        record.setDoctor(doctor);
        record.setAppointment(appointment);
        record.setRecordDate(LocalDate.now());
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
        return medicalRecordRepository.findByPatientId(patient.getId());
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
