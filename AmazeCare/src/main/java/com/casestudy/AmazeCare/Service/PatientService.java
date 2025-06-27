package com.casestudy.AmazeCare.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.casestudy.AmazeCare.Dto.PatientListingDto;
import com.casestudy.AmazeCare.Enum.Role;
import com.casestudy.AmazeCare.Enum.UserStatus;
import com.casestudy.AmazeCare.Exception.PatientNotFountException;
import com.casestudy.AmazeCare.Model.Patient;
import com.casestudy.AmazeCare.Model.User;
import com.casestudy.AmazeCare.Repoitory.PatientRepository;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PatientListingDto patientListingDto;

    public Patient insertPatient(Patient patient) {
        User user = patient.getUser();
        user.setRole(Role.PATIENT);
        user = userService.addUser(user);
        patient.setUser(user);
        return patientRepository.save(patient);
    }

    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    public Patient getName(String name) {
        return patientRepository.getbyUsername(name);
    }

    public Patient editPatientInfo(String username, Patient patient) {
        Patient oldPatient = patientRepository.getbyUsername(username);

        if (patient.getName() != null) oldPatient.setName(patient.getName());
        if (patient.getEmail() != null) oldPatient.setEmail(patient.getEmail());
        if (patient.getAddress() != null) oldPatient.setAddress(patient.getAddress());
        if (patient.getDob() != null) oldPatient.setDob(patient.getDob());
        if (patient.getEmergencyContact() != null) oldPatient.setEmergencyContact(patient.getEmergencyContact());
        if (patient.getGender() != null) oldPatient.setGender(patient.getGender());

        return patientRepository.save(oldPatient);
    }

    public Patient getById(int patient_id) {
        return patientRepository.getPatientById(patient_id)
                .orElseThrow(() -> new PatientNotFountException("Patient not Available or Active"));
    }

    public List<PatientListingDto> getByName(String name, int page, int size) {
        return patientListingDto.convertToDto(patientRepository.getbyName(name, PageRequest.of(page, size)));
    }

    public List<PatientListingDto> getAll(int page, int size) {
        return patientListingDto.convertToDto(patientRepository.getAll(PageRequest.of(page, size)));
    }

    public Patient editStatus(UserStatus status, int patient_id) {
        Patient patient = patientRepository.findById(patient_id)
                .orElseThrow(() -> new PatientNotFountException("Patient not Available"));
        patient.setUserStatus(status);
        return patientRepository.save(patient);
    }

    public Patient uploadProfilePic(MultipartFile file, String username) throws IOException {
        Patient patient = patientRepository.getbyUsername(username);
        if (patient == null) {
            throw new PatientNotFountException("Patient not found");
        }

        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || !originalFileName.contains(".")) {
            throw new RuntimeException("Invalid file");
        }

        String extension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1).toLowerCase();
        if (!List.of("jpg", "jpeg", "png").contains(extension)) {
            throw new RuntimeException("File type not supported");
        }

        if (file.getSize() > 3000 * 1024) {
            throw new RuntimeException("File too large (max 3MB)");
        }

        // Use a unique filename to avoid collisions
        String filename = System.currentTimeMillis() + "_" + originalFileName;

        // Absolute path for image storage
        String uploadPath = "D:\\\\HEXAWARE-TRAINING\\\\JAVA-REACT\\\\React\\\\hms_ui\\\\public\\\\images";
        Files.createDirectories(Path.of(uploadPath));

        Path fullPath = Paths.get(uploadPath, filename);
        Files.copy(file.getInputStream(), fullPath, StandardCopyOption.REPLACE_EXISTING);

        patient.setImageUrl(filename);
        return patientRepository.save(patient);
    }
}
