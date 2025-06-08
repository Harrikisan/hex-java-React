package com.casestudy.AmazeCare.Service;

import com.casestudy.AmazeCare.Enum.AppointmentStatus;
import com.casestudy.AmazeCare.Enum.PatientType;
import com.casestudy.AmazeCare.Exception.AppointmentNotFoundException;
import com.casestudy.AmazeCare.Exception.BedNotFoundException;
import com.casestudy.AmazeCare.Exception.PatientNotFountException;
import com.casestudy.AmazeCare.Model.Bed;
import com.casestudy.AmazeCare.Model.BedBooking;
import com.casestudy.AmazeCare.Model.Patient;
import com.casestudy.AmazeCare.Repoitory.BedBookingRepository;
import com.casestudy.AmazeCare.Repoitory.BedRepository;
import com.casestudy.AmazeCare.Repoitory.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BedBookingService {

    @Autowired
    private BedBookingRepository bedBookingRepository;
    private PatientRepository patientRepository;
    private BedRepository bedRepository;
    
    

    public BedBookingService(BedBookingRepository bedBookingRepository, PatientRepository patientRepository,
			BedRepository bedRepository) {
		super();
		this.bedBookingRepository = bedBookingRepository;
		this.patientRepository = patientRepository;
		this.bedRepository = bedRepository;
	}


	public BedBooking bookBed(BedBooking bedBooking, int patientId, int bedId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFountException("Patient not found with id: " + patientId));
        
        Bed bed = bedRepository.findById(bedId)
                .orElseThrow(() -> new BedNotFoundException("Bed not found with id: " + bedId));

        bedBooking.setPatirnt(patient);
        bedBooking.setBed(bed);
        bedBooking.setPatientType(PatientType.INPATIENT);
        bedBooking.setStatus(AppointmentStatus.APPOROVED);

        return bedBookingRepository.save(bedBooking);
    }


    public BedBooking getById(int id) {
        return bedBookingRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Booking not found"));
    }

    public List<BedBooking> getAll() {
        return bedBookingRepository.findAll();
    }

    public List<BedBooking> getByPatientId(int patientId) {
        return bedBookingRepository.findByPatirntId(patientId);
    }

    public List<BedBooking> getByUsername(String username) {
        Patient patient = patientRepository.getbyUsername(username);
        return bedBookingRepository.findByPatirntId(patient.getId());
    }

    public List<BedBooking> getByBedId(int bedId) {
        return bedBookingRepository.findByBedId(bedId);
    }

    public BedBooking cancelBooking(int bookingId) {
        BedBooking booking = getById(bookingId);
        booking.setStatus(AppointmentStatus.CANCELED);
        return bedBookingRepository.save(booking);
    }
}
