package com.casestudy.AmazeCare.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casestudy.AmazeCare.Exception.MedicalRecordNotFoundException;
import com.casestudy.AmazeCare.Model.MedicalRecord;
import com.casestudy.AmazeCare.Model.Prescription;
import com.casestudy.AmazeCare.Repoitory.MedicalRecordRepository;
import com.casestudy.AmazeCare.Repoitory.PrescriptionRepository;

@Service
public class PrescriptionService {

    
    private PrescriptionRepository prescriptionRepository;
    private MedicalRecordRepository medicalRecordRepository;

    public PrescriptionService(PrescriptionRepository prescriptionRepository,
			MedicalRecordRepository medicalRecordRepository) {
		super();
		this.prescriptionRepository = prescriptionRepository;
		this.medicalRecordRepository = medicalRecordRepository;
	}

	public Prescription addPrescription(int recordId, Prescription prescription) {
        MedicalRecord record = medicalRecordRepository.findById(recordId)
                .orElseThrow(() -> new MedicalRecordNotFoundException("Medical record not found with ID: " + recordId));

        prescription.setMedicalRecord(record);
        return prescriptionRepository.save(prescription);
    }

    public List<Prescription> getPrescriptionsByRecordId(int recordId) {
        return prescriptionRepository.findByMedicalRecordId(recordId);
    }
}
