package com.casestudy.AmazeCare.Service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
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

    public List<Prescription> addPrescriptionsBatch(int recordId, List<Prescription> prescriptions) {
        MedicalRecord record = medicalRecordRepository.findById(recordId)
                .orElseThrow(() -> new MedicalRecordNotFoundException("Medical record not found with ID: " + recordId));

        prescriptions.forEach(prescription -> prescription.setMedicalRecord(record));

        return prescriptionRepository.saveAll(prescriptions);
    }


    public List<Prescription> getPrescriptionsByRecordId(int recordId) {
        return prescriptionRepository.getByMedicalRecordId(recordId);
    }

}
