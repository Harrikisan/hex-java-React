package com.casestudy.AmazeCare;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.casestudy.AmazeCare.Exception.MedicalRecordNotFoundException;
import com.casestudy.AmazeCare.Exception.PatientNotFountException;
import com.casestudy.AmazeCare.Model.Doctor;
import com.casestudy.AmazeCare.Model.MedicalRecord;
import com.casestudy.AmazeCare.Model.Patient;
import com.casestudy.AmazeCare.Repoitory.DoctorAppointmentRepository;
import com.casestudy.AmazeCare.Repoitory.DoctorRepository;
import com.casestudy.AmazeCare.Repoitory.MedicalRecordRepository;
import com.casestudy.AmazeCare.Repoitory.PatientRepository;
import com.casestudy.AmazeCare.Service.MedicalRecordService;

@SpringBootTest
public class MedicalRecordServiceTest {

    @InjectMocks
    private MedicalRecordService medicalRecordService;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private DoctorAppointmentRepository appointmentRepository;

    @Mock
    private PatientRepository patientRepository;

    private MedicalRecord record;
    private Doctor doctor;
    private Patient patient;

    @BeforeEach
    public void setup() {
        doctor = new Doctor();
        doctor.setId(1);
        doctor.setName("Dr. Kumar");

        patient = new Patient();
        patient.setId(100);
        patient.setName("Sita");

        record = new MedicalRecord();
        record.setId(10);
        record.setNotes("Headache");
        record.setDiagnosis("Migraine");
    }

    @Test
    public void testAddMedicalRecord() {
        when(doctorRepository.getByUsername("dr.kumar")).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(100)).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.save(record)).thenReturn(record);

        MedicalRecord result = medicalRecordService.addMedicalRecord(record, 100, "dr.kumar");
        assertEquals(patient, result.getPatient());
        assertEquals(doctor, result.getDoctor());

        // Failure case: Doctor not found
        when(doctorRepository.getByUsername("invalid")).thenReturn(Optional.empty());
        assertThrows(MedicalRecordNotFoundException.class, () ->
                medicalRecordService.addMedicalRecord(record, 100, "invalid"));

        // Failure case: Patient not found
        when(doctorRepository.getByUsername("dr.kumar")).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(999)).thenReturn(Optional.empty());
        assertThrows(PatientNotFountException.class, () ->
                medicalRecordService.addMedicalRecord(record, 999, "dr.kumar"));
    }

    @Test
    public void testGetMedicalRecordById() {
        when(medicalRecordRepository.findById(10)).thenReturn(Optional.of(record));
        assertEquals(record, medicalRecordService.getMedicalRecordById(10));

        when(medicalRecordRepository.findById(999)).thenReturn(Optional.empty());
        assertThrows(MedicalRecordNotFoundException.class, () ->
                medicalRecordService.getMedicalRecordById(999));
    }

    @Test
    public void testGetAllMedicalRecords() {
        when(medicalRecordRepository.findAll()).thenReturn(List.of(record));
        assertEquals(1, medicalRecordService.getAllMedicalRecords().size());
    }

    @Test
    public void testGetMedicalRecordsByPatientUsername() {
        when(patientRepository.getbyUsername("sita"))
            .thenReturn(patient);
        when(medicalRecordRepository.findByPatientUsername(100))
            .thenReturn(List.of(record));
        assertEquals(1, medicalRecordService.getMedicalRecordsByPatientUsername("sita").size());
    }

    @Test
    public void testGetMedicalRecordsByPatientId() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<MedicalRecord> page = new PageImpl<>(List.of(record));

        when(medicalRecordRepository.findByPatientId(100, pageable)).thenReturn(page);
        List<MedicalRecord> result = medicalRecordService.getMedicalRecordsByPatientId(100, 0, 5);
        assertEquals(1, result.size());
    }

    @Test
    public void testGetMedicalRecordsByDoctorUsername() {
        when(doctorRepository.getByUsername("dr.kumar")).thenReturn(Optional.of(doctor));
        when(medicalRecordRepository.findByDoctorId(1)).thenReturn(List.of(record));
        assertEquals(1, medicalRecordService.getMedicalRecordsByDoctorUsername("dr.kumar").size());

        when(doctorRepository.getByUsername("unknown")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () ->
                medicalRecordService.getMedicalRecordsByDoctorUsername("unknown"));
    }

    @Test
    public void testGetMedicalRecordsByRecordDate() {
        LocalDate date = LocalDate.now();
        when(medicalRecordRepository.findByRecordDate(date)).thenReturn(List.of(record));
        assertEquals(1, medicalRecordService.getMedicalRecordsByRecordDate(date).size());
    }

    @AfterEach
    public void delete() {
        doctor = null;
        patient = null;
        record = null;
    }
}
