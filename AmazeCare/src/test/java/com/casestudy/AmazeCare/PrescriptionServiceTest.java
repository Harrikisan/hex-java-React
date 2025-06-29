package com.casestudy.AmazeCare;

import com.casestudy.AmazeCare.Exception.MedicalRecordNotFoundException;

import com.casestudy.AmazeCare.Model.MedicalRecord;
import com.casestudy.AmazeCare.Model.Prescription;
import com.casestudy.AmazeCare.Repoitory.MedicalRecordRepository;
import com.casestudy.AmazeCare.Repoitory.PrescriptionRepository;
import com.casestudy.AmazeCare.Service.PrescriptionService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PrescriptionServiceTest {

    @InjectMocks
    private PrescriptionService prescriptionService;

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    private MedicalRecord medicalRecord;
    private List<Prescription> prescriptionList;

    @BeforeEach
    public void setUp() {
        medicalRecord = new MedicalRecord();
        medicalRecord.setId(101);

        Prescription prescription1 = new Prescription();
        prescription1.setId(1);
        prescription1.setMedicineName("Paracetamol");

        Prescription prescription2 = new Prescription();
        prescription2.setId(2);
        prescription2.setMedicineName("Ibuprofen");

        prescriptionList = Arrays.asList(prescription1, prescription2);
    }

    @Test
    public void testAddPrescriptionsBatch() {
        when(medicalRecordRepository.findById(101)).thenReturn(Optional.of(medicalRecord));
        when(prescriptionRepository.saveAll(prescriptionList)).thenReturn(prescriptionList);

        List<Prescription> result = prescriptionService.addPrescriptionsBatch(101, prescriptionList);
        assertEquals(2, result.size());
        assertEquals("Paracetamol", result.get(0).getMedicineName());

        when(medicalRecordRepository.findById(999)).thenReturn(Optional.empty());
        MedicalRecordNotFoundException exception = assertThrows(MedicalRecordNotFoundException.class, () ->
                prescriptionService.addPrescriptionsBatch(999, prescriptionList));
        assertEquals("Medical record not found with ID: 999", exception.getMessage());
    }

    @Test
    public void testGetPrescriptionsByRecordId() {
        when(prescriptionRepository.getByMedicalRecordId(101)).thenReturn(prescriptionList);

        List<Prescription> result = prescriptionService.getPrescriptionsByRecordId(101);
        assertEquals(2, result.size());
        verify(prescriptionRepository, times(1)).getByMedicalRecordId(101);
    }

    @AfterEach
    public void Delete() {
        medicalRecord = null;
        prescriptionList = null;
    }
}
