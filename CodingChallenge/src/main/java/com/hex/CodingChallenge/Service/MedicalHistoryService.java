package com.hex.CodingChallenge.Service;

import org.springframework.stereotype.Service;

import com.hex.CodingChallenge.Dto.PatientMedicalRecordDto;
import com.hex.CodingChallenge.Model.MedicalHistory;
import com.hex.CodingChallenge.Model.Patient;
import com.hex.CodingChallenge.Repository.MedicalHistoryRepository;
@Service
public class MedicalHistoryService {

	private MedicalHistoryRepository medicalHistoryRepository;
	private PatientService patientService;

	public MedicalHistoryService(MedicalHistoryRepository medicalHistoryRepository, PatientService patientService) {
		super();
		this.medicalHistoryRepository = medicalHistoryRepository;
		this.patientService = patientService;
	}



	public MedicalHistory add(PatientMedicalRecordDto patdto) {
		Patient patient= patientService.add(patdto.getPatient());
		patdto.getMedicalHistory().setPatient(patient);
		return medicalHistoryRepository.save(patdto.getMedicalHistory());
	}

}
